package org.project.service;

import org.project.util.PageSize;
import org.project.util.SortingOrder;

import java.util.List;
import java.util.Map;

public interface CrudService<T> {
    T readById(long id);
    List<T> readAll();
    List<T> readAllSorted(SortingOrder order);
    void delete(T element);
    T create(T element);
    T update(T element);
    Map<Integer, List<T>> getAllInPages(PageSize pageSize);
}