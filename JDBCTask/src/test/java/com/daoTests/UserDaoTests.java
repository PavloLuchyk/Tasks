package com.daoTests;

import static org.junit.jupiter.api.Assertions.*;

import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.postgres.UserDaoPostgres;
import com.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;


public class UserDaoTests {

    private static Connection connection;
    private static CrudDao<User> userCrudDao;
    private static Savepoint savepoint;

    @BeforeAll
    public static void init() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()){
            statement.execute("BEGIN;");
        }
        savepoint = connection.setSavepoint("Savepoint");
        userCrudDao = new UserDaoPostgres(connection);
    }

    @Test
    public void createUserTest() throws SQLException {
        User user = new User(0, "User", "user");
        User actual = userCrudDao.create(user);
        connection.commit();
        assertEquals(user, actual);
    }

    @Test
    public void updateUserTest() throws SQLException {
        User expected = userCrudDao.create(new User(0, "User", "password"));
        expected.setFullName("User1");
        User actual = userCrudDao.update(expected);
        connection.commit();
        assertEquals(expected, actual);
    }

    @Test
    public void readByIdUserTest() throws SQLException {
        User expected = userCrudDao.create(new User(0, "User", "password"));
        User actual = userCrudDao.readById(expected.getId());
        connection.commit();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserTest() throws SQLException {
        User user = userCrudDao.create(new User(0,"user", "user"));
        userCrudDao.delete(user);
        assertThrows(SQLException.class, () ->userCrudDao.readById(user.getId()));
    }

    @AfterAll
    public static void destroy() throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute("ROLLBACK;");
        }
    }
}
