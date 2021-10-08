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
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdvertisementTests {

    private static Connection connection;

    private static CrudDao<Advertisement> advertisementCrudDao;

    private static User user = Mockito.mock(User.class);

    private static String date = "2021-09-30T12:45:30";

    @BeforeAll
    public static void init() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
        advertisementCrudDao = new AdvertisementDaoPostgres(connection);
        Mockito.when(user.getId()).thenReturn(2L);
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
        Advertisement expected = new Advertisement(0, "Title", LocalDateTime.parse(date), "Some text", user);
        expected = advertisementCrudDao.create(expected);
        expected.setComments(new ArrayList<>());
        Advertisement actual = advertisementCrudDao.readById(expected.getId());
        actual.setOwner(user);
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
