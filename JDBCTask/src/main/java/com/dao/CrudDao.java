package com.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {
    T create(T element) throws SQLException;
    T update(T element) throws SQLException;
    List<T> readAll() throws SQLException;
    T readById(long id) throws SQLException;
    void delete(T element) throws SQLException;
}
