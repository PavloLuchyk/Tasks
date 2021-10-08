package org.project.repository;

import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public interface CrudRepository<T> {
    T readById(long id);
    List<T> readAll();
    List<T> readAllSorted(SortingOrder order);
    void delete(T element);
    Serializable create(T element);
    T update(T element);
    Map<Integer, List<T>> getAllInPages(PageSize pageSize);
}
