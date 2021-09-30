package com.dao;

import com.entities.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao extends CrudDao<Comment> {
    List<Comment> getAllCommentsByUserId(long userId, SortingOrder order) throws SQLException;
}
