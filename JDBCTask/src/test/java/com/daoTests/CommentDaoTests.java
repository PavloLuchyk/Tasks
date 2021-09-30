package com.daoTests;

import com.dao.CommentDao;
import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.dao.postgres.UserDaoPostgres;
import com.entities.Advertisement;
import com.entities.Comment;
import com.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentDaoTests {
    private static Connection connection;
    private static CommentDao commentDao;
    private static User user;
    private static Advertisement advertisement;

    @BeforeAll
    public static void init() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
        CrudDao<User> userCrudDao = new UserDaoPostgres(connection);
        CrudDao<Advertisement> advertisementCrudDao = new AdvertisementDaoPostgres(connection);
        commentDao = new CommentDaoPostgres(connection);
        user = userCrudDao.create(new User(0, "User", "password"));
        advertisement = advertisementCrudDao.create(
                new Advertisement(0, "title", LocalDateTime.now(), "description", user));
    }

    @Test
    public void createCommentTest() throws SQLException {
        Comment expected = new Comment(0, LocalDateTime.now(), "Text", user, advertisement);
        Comment actual = commentDao.create(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void updateCommentTest() throws SQLException {
        Comment expected = commentDao.create(new Comment(0, LocalDateTime.now(), "Text", user, advertisement));
        expected.setText("smth");
        Comment actual = commentDao.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void readByIdCommentTest() throws SQLException {
        Comment expected = new Comment(0, LocalDateTime.now(), "Text", user, advertisement);
        expected = commentDao.create(expected);
        Comment actual = commentDao.readById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteCommentTest() throws SQLException {
        Comment comment = commentDao.create(new Comment(0, LocalDateTime.now(), "Text", user, advertisement));
        commentDao.delete(comment);
        assertThrows(SQLException.class, ()->commentDao.readById(comment.getId()));
    }

    @Test
    public void getAllCommentsByUserIdTest() throws SQLException {
        List<Comment> expected = new ArrayList<>();
        expected.add(commentDao.create(new Comment(0, LocalDateTime.now(), "Text", user, advertisement)));
        expected.add(commentDao.create(new Comment(0, LocalDateTime.now(), "Text2", user, advertisement)));
        expected.add(commentDao.create(new Comment(0, LocalDateTime.now(), "Text3", user, advertisement)));
        List<Comment> actual = commentDao.getAllCommentsByUserId(user.getId());
        assertEquals(expected, actual);
    }
}
