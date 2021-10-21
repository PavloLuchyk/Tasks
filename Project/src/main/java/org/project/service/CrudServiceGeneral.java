package org.project.service;

import org.project.repository.CrudRepository;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public abstract class CrudServiceGeneral<T> implements CrudService<T> {

    private CrudRepository<T> crudRepository;

    @Autowired
    public CrudServiceGeneral(CrudRepository<T> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public T readById(long id) {
        if (id <= 0){
            throw new IllegalArgumentException("Id cannot be lesser or equal zero!");
        }
        return crudRepository.readById(id);
    }

    @Override
    public List<T> readAll() {
        return crudRepository.readAll();
    }

    @Override
    public List<T> readAllSorted(SortingOrder order) {
        return crudRepository.readAllSorted(order);
    }

    @Override
    @Transactional
    public void delete(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        crudRepository.delete(element);
    }

    @Override
    @Transactional
    public T update(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return crudRepository.update(element);
    }

    @Override
    public Map<Integer, List<T>> getAllInPages(PageSize pageSize) {
        if (pageSize == null) {
            throw new IllegalArgumentException("Page size cannot be null!");
        }
        return crudRepository.getAllInPages(pageSize);
    }
}
