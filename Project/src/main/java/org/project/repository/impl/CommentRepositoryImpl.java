package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Advertisement;
import org.project.model.Comment;
import org.project.repository.CommentRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class CommentRepositoryImpl extends CrudRepositoryGeneral<Comment> implements CommentRepository {

    @Autowired
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Comment> getAllByParentId(long parentId, String parentName) {
        CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(clazz);
        Root<Comment> from = cq.from(clazz);
        cq.select(from);
        CriteriaQuery<Comment> query = cq.where(cb.equal(from.get(parentName).get("id"),parentId));
        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
    }
}
