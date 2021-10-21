package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Author;
import org.project.model.Category;
import org.project.repository.CategoryRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
public class CategoryRepositoryImpl extends CrudRepositoryGeneral<Category> implements CategoryRepository {

    @Autowired
    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean checkUniqueName(String name) {
        try {
            CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
            CriteriaQuery<Category> cq = cb.createQuery(clazz);
            Root<Category> from = cq.from(clazz);
            cq.select(from);
            CriteriaQuery<Category> query = cq.where(cb.equal(from.get("name"), name));
            Category category = sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return true;
        }
        return false;
    }
}
