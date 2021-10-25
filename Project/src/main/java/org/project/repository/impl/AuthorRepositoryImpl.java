package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Author;
import org.project.repository.AuthorRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class AuthorRepositoryImpl extends CrudRepositoryGeneral<Author> implements AuthorRepository {

    @Autowired
    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public Author getByEmail(String email) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(clazz);
        Root<Author> from = cq.from(clazz);
        cq.select(from);
        CriteriaQuery<Author> query = cq.where(cb.equal(from.get("email"), email));
        return sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
    }

    @Override
    public boolean checkUniqueByEmail(String email) {
        try {
            getByEmail(email);
        } catch (NoResultException e) {
            return true;
        }
        return false;
    }
}
