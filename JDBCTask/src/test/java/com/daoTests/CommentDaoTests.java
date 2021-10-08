package com.daoTests;

import com.dao.CommentDao;
import com.dao.CrudDao;
import com.dao.DataSource;
import com.dao.SortingOrder;
import com.dao.postgres.AdvertisementDaoPostgres;
import com.dao.postgres.CommentDaoPostgres;
import com.dao.postgres.UserDaoPostgres;
import com.entities.Advertisement;
import com.entities.Comment;
import com.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentDaoTests {

    private static Connection connection;

    private static CommentDao commentDao;

    private static User user = Mockito.mock(User.class);

    private static Advertisement advertisement = Mockito.mock(Advertisement.class);

    private static String date = "2021-09-30T12:45:30";

    @BeforeAll
    public static void init() throws SQLException {
        connection = DataSource.getConnection();
        connection.setAutoCommit(false);
        commentDao = new CommentDaoPostgres(connection);
        Mockito.when(user.getId()).thenReturn(2L);
        Mockito.when(advertisement.getId()).thenReturn(34L);
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
        Comment expected = new Comment(0, LocalDateTime.parse(date), "Text", user, advertisement);
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
        User user = new UserDaoPostgres(connection).create(new User(0, "User", "pass"));
        List<Comment> expected = new ArrayList<>();
        expected.add(commentDao.create(new Comment(0, LocalDateTime.parse(date), "Text", user, advertisement)));
        expected.add(commentDao.create(new Comment(0, LocalDateTime.parse(date), "Text2", user, advertisement)));
        expected.add(commentDao.create(new Comment(0, LocalDateTime.parse(date), "Text3", user, advertisement)));
        List<Comment> actual = commentDao.getAllCommentsByUserId(user.getId(), SortingOrder.ASC);
        assertEquals(expected, actual);
    }
}
