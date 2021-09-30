package com.dao.postgres;

import com.dao.CommentDao;
import com.entities.Advertisement;
import com.entities.Comment;
import com.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoPostgres implements CommentDao {

    private final Connection con;
    private static final String CREATE_SQL =
            "insert into comments(creationdate, text, userId, advertismentid) values (?,?,?,?);";
    private static final String DELETE_SQL =
            "delete from comments where id=?;";
    private static final String UPDATE_SQL =
            "update comments set creationdate=?, text=?, userId=?, advertismentid=? " +
                    "where id=?;";
    private static final String SELECT_SQL =
            "select comments.*, users.fullname, users.password, advertisements1.*  " +
                    "from comments " +
                    "inner join users on comments.userid = users.id " +
                    "inner join ( " +
                    " select advertisements.*, users.fullname, users.password " +
                    " from advertisements inner join users on advertisements.userid = users.id " +
                    ") as advertisements1 " +
                    "on comments.advertismentid = advertisements1.id;";
    private static final String SELECT_BY_ID_SQL =
            "select comments.*, users.fullname, users.password, advertisements1.*  " +
                    "from comments " +
                    "inner join users on comments.userid = users.id " +
                    "inner join ( " +
                    " select advertisements.*, users.fullname, users.password " +
                    " from advertisements inner join users on advertisements.userid = users.id " +
                    ") as advertisements1 " +
                    "on comments.advertismentid = advertisements1.id " +
                    "where comments.id = ?;";
    private static final String SELECT_BY_USER_ID_SQL =
            "select comments.*, users.fullname, users.password, advertisements1.*  " +
                    "from comments " +
                    "inner join users on comments.userid = users.id " +
                    "inner join ( " +
                    " select advertisements.*, users.fullname, users.password " +
                    " from advertisements inner join users on advertisements.userid = users.id " +
                    ") as advertisements1 " +
                    "on comments.advertismentid = advertisements1.id " +
                    "where comments.userid = ? " +
                    "order by comments.creationdate desc;";

    public CommentDaoPostgres(Connection con) {
        this.con = con;
    }

    @Override
    public Comment create(Comment element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_SQL)) {
                preparedStatement.setTimestamp(1, Timestamp.valueOf(element.getCreationDate()));
                preparedStatement.setString(2, element.getText());
                preparedStatement.setLong(3, element.getOwner().getId());
                preparedStatement.setLong(4, element.getAdvertisement().getId());
                preparedStatement.execute();
                return element;
            }
        }
        throw new NullPointerException("Null value cannot be created");
    }

    @Override
    public Comment update(Comment element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setTimestamp(1, Timestamp.valueOf(element.getCreationDate()));
                preparedStatement.setString(2, element.getText());
                preparedStatement.setLong(3, element.getOwner().getId());
                preparedStatement.setLong(4, element.getAdvertisement().getId());
                preparedStatement.setLong(5, element.getId());
                preparedStatement.execute();
                return element;
            }
        }
        throw new NullPointerException("Null value cannot be updated");
    }

    @Override
    public List<Comment> readAll() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                comments.add(getCommentFromResultSet(rs));
            }
        }
        return comments;
    }

    @Override
    public Comment readById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return getCommentFromResultSet(rs);
                }
            }
        }
        throw new SQLException("Comment with id " + id + " not found");
    }

    private Comment getCommentFromResultSet(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getInt(1),
                rs.getTimestamp(2).toLocalDateTime(),
                rs.getString(3),
                new User(
                        rs.getInt(4),
                        rs.getString(6),
                        rs.getString(7)
                ),
                new Advertisement(
                        rs.getInt(5),
                        rs.getString(9),
                        rs.getTimestamp(10).toLocalDateTime(),
                        rs.getString(11),
                        new User(
                                rs.getInt(12),
                                rs.getString(13),
                                rs.getString(14)
                        )
                )
        );
    }

    @Override
    public void delete(Comment element) throws SQLException {
        if (element != null) {
            try (PreparedStatement preparedStatement= con.prepareStatement(DELETE_SQL)) {
                preparedStatement.setLong(1, element.getId());
                preparedStatement.execute();
                return;
            }
        }
        throw new NullPointerException("Null value cannot be removed");
    }

    @Override
    public List<Comment> getAllCommentsByUserId(long userId) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_USER_ID_SQL)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    comments.add(getCommentFromResultSet(rs));
                }
            }
        }
        return comments;
    }
}
