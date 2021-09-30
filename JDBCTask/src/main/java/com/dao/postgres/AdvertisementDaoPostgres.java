package com.dao.postgres;

import com.dao.CrudDao;
import com.entities.Advertisement;
import com.entities.Comment;
import com.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementDaoPostgres implements CrudDao<Advertisement> {

    private final Connection con;
    private static final String CREATE_SQL =
            "insert into advertisements(textfield, creationdate, description, userId) values (?,?,?,?);";
    private static final String DELETE_SQL =
            "delete from advertisements where id=?;";
    private static final String UPDATE_SQL =
            "update advertisements set textfield=?, creationdate=?, description=?, userId=? " +
                    "where id=?;";
    private static final String SELECT_SQL =
            "select advertisements.*, users.fullname, users.password, " +
                    "comments1.id, comments1.creationdate, comments1.text, comments1.userid, comments1.fullname, comments1.password " +
                    "from advertisements " +
                    "inner join users on advertisements.userId = users.id " +
                    "left join " +
                    "(select comments.*, users.fullname, users.password " +
                    " from comments inner join users on comments.userId=users.id) " +
                    "as comments1 on comments1.advertismentid = advertisements.id;";
    private static final String SELECT_BY_ID_SQL =
            "select advertisements.*, users.fullname, users.password, " +
                    "comments1.id, comments1.creationdate, comments1.text, comments1.userid, comments1.fullname, comments1.password " +
                    "from advertisements " +
                    "inner join users on advertisements.userId = users.id " +
                    "left join " +
                    "(select comments.*, users.fullname, users.password " +
                    " from comments inner join users on comments.userId=users.id) " +
                    "as comments1 on comments1.advertismentid = advertisements.id " +
                    "where advertisements.id = ?;";

    public AdvertisementDaoPostgres(Connection con) {
        this.con = con;
    }

    @Override
    public Advertisement create(Advertisement element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, element.getTitle());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(element.getCreationDate()));
                preparedStatement.setString(3, element.getDescription());
                preparedStatement.setLong(4, element.getOwner().getId());
                preparedStatement.execute();
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()&&rs != null) {
                        element.setId(rs.getInt(1));
                    } else {
                        throw new SQLException("Error reading generated key");
                    }
                }
                return element;
            }
        }
        throw new NullPointerException("Null value cannot be inserted");
    }

    @Override
    public Advertisement update(Advertisement element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setString(1, element.getTitle());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(element.getCreationDate()));
                preparedStatement.setString(3, element.getDescription());
                preparedStatement.setLong(4, element.getOwner().getId());
                preparedStatement.setLong(5, element.getId());
                preparedStatement.execute();
                return element;
            }
        }
        throw new NullPointerException("Null value cannot be updated");
    }

    @Override
    public List<Advertisement> readAll() throws SQLException {
        List<Advertisement> advertisements = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SELECT_SQL);
             ResultSet rs = statement.executeQuery()){
            rs.next();
            while (!rs.isAfterLast()) {
                Advertisement advertisement = new Advertisement(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3).toLocalDateTime(),
                        rs.getString(4),
                        new User(
                                rs.getInt(5),
                                rs.getString(6),
                                rs.getString(7)
                        )
                );
                List<Comment> comments = new ArrayList<>();
                while (rs.getInt(1) == advertisement.getId()) {
                    if (rs.getInt(8) != 0) {
                        comments.add(
                                new Comment(
                                        rs.getInt(8),
                                        rs.getTimestamp(9).toLocalDateTime(),
                                        rs.getString(10),
                                        new User(
                                                rs.getInt(11),
                                                rs.getString(12),
                                                rs.getString(13)
                                        ),
                                        advertisement
                                )
                        );
                    }
                    rs.next();
                    if (rs.isAfterLast()) {
                        break;
                    }
                }
                advertisement.setComments(comments);
                advertisements.add(advertisement);

            }

        }
        return advertisements;
    }

    @Override
    public Advertisement readById(long id) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Advertisement advertisement = new Advertisement(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getTimestamp(3).toLocalDateTime(),
                            rs.getString(4),
                            new User(
                                    rs.getInt(5),
                                    rs.getString(6),
                                    rs.getString(7)
                            )
                    );
                    List<Comment> comments = new ArrayList<>();
                    do {
                        if (rs.getInt(8) != 0) {
                            comments.add(
                                    new Comment(
                                            rs.getInt(8),
                                            rs.getTimestamp(9).toLocalDateTime(),
                                            rs.getString(10),
                                            new User(
                                                    rs.getInt(11),
                                                    rs.getString(12),
                                                    rs.getString(13)
                                            ),
                                            advertisement
                                    )
                            );
                        }
                    } while (rs.next());
                    advertisement.setComments(comments);
                    return advertisement;
                }
            }
        }
        throw new SQLException("Advertisement with id " + id + " not found!");
    }

    @Override
    public void delete(Advertisement element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement= con.prepareStatement(DELETE_SQL)) {
                preparedStatement.setLong(1, element.getId());
                preparedStatement.execute();
                return;
            }
        }
        throw new NullPointerException("Null value cannot be removed");
    }
}
