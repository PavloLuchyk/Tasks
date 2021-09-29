package com.dao.postgres;

import com.dao.CrudDao;
import com.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres implements CrudDao<User> {

    private final Connection con;
    private static final String CREATE_SQL = "insert into users (fullName, password) values (?,?);";
    private static final String DELETE_SQL = "delete from users where id=?;";
    private static final String UPDATE_SQL = "update users set fullName=?, password=? where id=?;";
    private static final String SELECT_SQL = "select * from users;";
    private static final String SELECT_BY_ID_SQL = "select * from users where id=?;";


    public UserDaoPostgres(Connection con) {
        this.con = con;
    }

    @Override
    public User create(User user) throws SQLException{
        if (user != null) {
            try (PreparedStatement preparedStatement= con.prepareStatement(CREATE_SQL)) {
                preparedStatement.setString(1, user.getFullName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.execute();
                return user;
            }
        }
        throw new NullPointerException("Null value cannot be added");
    }

    @Override
    public User update(User element) throws SQLException{
        if (element != null) {
            try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setString(1, element.getFullName());
                preparedStatement.setString(2, element.getPassword());
                preparedStatement.setLong(3, element.getId());
                preparedStatement.execute();
                return element;
            }
        }
        throw new NullPointerException("Null value cannot be updated");
    }

    @Override
    public List<User> readAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SELECT_SQL);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        }
        return users;
    }

    @Override
    public User readById(long id) throws SQLException{
        try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try(ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)

                    );
                }
            }
        }
        throw new SQLException("User with id " + id + " not found!");
    }

    @Override
    public void delete(User element) throws SQLException{
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
