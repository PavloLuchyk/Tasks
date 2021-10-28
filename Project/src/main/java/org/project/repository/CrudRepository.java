package org.project.repository;

import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
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
    List<T> getAllInPages(PageSize pageSize, int pageNumber);
    Long getCountOfAllPages(PageSize pageSize);
}
