package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.enums.PageSize;
import org.project.model.Advertisement;
import org.project.model.Comment;
import org.project.repository.CommentRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
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
        return sessionFactory.getCurrentSession().createQuery(getParentQuery(parentId,parentName)).getResultList();
    }

    @Override
    public List<Comment> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber) {
        TypedQuery<Comment> typedQuery=
                sessionFactory.getCurrentSession().createQuery(getParentQuery(parentId, parentName));
        typedQuery.setFirstResult(pageNumber*pageSize.size);
        typedQuery.setMaxResults(pageSize.size);
        return typedQuery.getResultList();
    }

    @Override
    public Number getTotalCountOfPages(long parentId, String parentName, PageSize pageSize) {
        TypedQuery<Number> query = sessionFactory.getCurrentSession()
                .createQuery("SELECT count(a) from Comment a where " +
                        "a."+parentName+".id"
                        +" = :parentId", Number.class)
                .setParameter("parentId", parentId);
        return  query.getSingleResult();
    }

    private CriteriaQuery<Comment> getParentQuery(long parentId, String parentName) {
        CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(clazz);
        Root<Comment> from = cq.from(clazz);
        cq.select(from);
        return cq.where(cb.equal(from.get(parentName).get("id"), parentId)).orderBy(cb.desc(from.get("createDate")));
    }
}
