package com.daoTests;

import static org.junit.jupiter.api.Assertions.*;

import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.UserDaoPostgres;
import com.entities.Advertisement;
import com.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdvertisementTests {

    private static Connection connection;
    private static CrudDao<Advertisement> advertisementCrudDao;
    private static User user;

    @BeforeAll
    public static void init() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
        CrudDao<User> userCrudDao = new UserDaoPostgres(connection);
        advertisementCrudDao = new AdvertisementDaoPostgres(connection);
        user = userCrudDao.create(new User(0, "User", "password"));
    }

    @Test
    public void createAdvertisementTest() throws SQLException {
        Advertisement expected = new Advertisement(0, "Title", LocalDateTime.now(), "Some text", user);
        Advertisement actual = advertisementCrudDao.create(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void updateAdvertisementTest() throws SQLException {
        Advertisement expected = new Advertisement(0, "Title", LocalDateTime.now(), "Some text", user);
        expected = advertisementCrudDao.create(expected);
        expected.setDescription("Another text");
        Advertisement actual = advertisementCrudDao.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void readByIdAdvertisementTest() throws SQLException {
        Advertisement expected = new Advertisement(0, "Title", LocalDateTime.now(), "Some text", user);
        expected = advertisementCrudDao.create(expected);
        Advertisement actual = advertisementCrudDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteAdvertisementTest() throws SQLException {
        Advertisement advertisement;
        advertisement =
                advertisementCrudDao.create(
                        new Advertisement(0, "Title", LocalDateTime.now(), "Some text", user)
                );
        advertisementCrudDao.delete(advertisement);
        assertThrows(SQLException.class, ()->advertisementCrudDao.readById(advertisement.getId()));
    }
}
