package org.project.repository;

import org.hibernate.SessionFactory;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public abstract class CrudRepositoryGeneral<T> implements CrudRepository<T>{

    protected SessionFactory sessionFactory;
    protected final Class<T> clazz;

    @Autowired
    public CrudRepositoryGeneral(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.clazz = (Class<T>) GenericTypeResolver
                .resolveTypeArgument(getClass(), CrudRepositoryGeneral.class);
    }

    @Override
    public List<T> readAll() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = sessionFactory.getCurrentSession().createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<T> readAllSorted(SortingOrder order) {
        CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> from = cq.from(clazz);
        cq.select(from);
        CriteriaQuery<T> query = cq.orderBy(order.equals(SortingOrder.ASC)?
                                                        cb.asc(from.get("id")):
                                                        cb.desc(from.get("id")));
        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
    }

    @Override
    public T readById(long id) {
        return sessionFactory.getCurrentSession().get(clazz, id);
    }


    @Override
    public void delete(T element) {
        sessionFactory.getCurrentSession().delete(element);
    }


    @Override
    public T update(T element) {
        sessionFactory.getCurrentSession().update(element);
        return element;
    }


    @Override
    public Serializable create(T element) {
        return sessionFactory.getCurrentSession().save(element);
    }

    @Override
    public List<T> getAllInPages(PageSize pageSize, int pageNumber) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        int offset = pageNumber*pageSize.size;
        CriteriaQuery<T> criteriaQuery = cb.createQuery(clazz);
        Root<T> from = criteriaQuery.from(clazz);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        TypedQuery<T> typedQuery = sessionFactory.getCurrentSession().createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(pageSize.size);
        return typedQuery.getResultList();
    }

    @Override
    public Long getCountOfAllPages(PageSize pageSize) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Long> pageCriteriaQuery = cb.createQuery(Long.class);
        pageCriteriaQuery.select(cb.count(pageCriteriaQuery.from(clazz)));
        long totalNumber = sessionFactory.getCurrentSession().createQuery(pageCriteriaQuery).getSingleResult();
        return totalNumber;
    }
}
