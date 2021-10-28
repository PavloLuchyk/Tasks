package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.enums.PageSize;
import org.project.model.Advertisement;
import org.project.repository.AdvertisementRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AdvertisementRepositoryImpl extends CrudRepositoryGeneral<Advertisement> implements AdvertisementRepository {

    @Autowired
    public AdvertisementRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Advertisement> getAllByParentId(long parentId, String parentName) {
        return sessionFactory.getCurrentSession().createQuery(getParentQuery(parentId, parentName)).getResultList();
    }

    @Override
    public List<Advertisement> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber) {
        TypedQuery<Advertisement> typedQuery=
                sessionFactory.getCurrentSession().createQuery(getParentQuery(parentId, parentName));
        typedQuery.setFirstResult(pageNumber*pageSize.size);
        typedQuery.setMaxResults(pageSize.size);
        return typedQuery.getResultList();
    }

    @Override
    public Long getTotalCountOfPages(long parentId, String parentName, PageSize pageSize) {
        TypedQuery<Long> query = sessionFactory.getCurrentSession()
                .createQuery("SELECT count(a) from Advertisement a where " +
                        "a."+parentName+".id"
                        +" = :parentId")
                .setParameter("parentId", parentId);
        return  query.getSingleResult();
    }

    private CriteriaQuery<Advertisement> getParentQuery(long parentId, String parentName) {
        CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Advertisement> cq = cb.createQuery(clazz);
        Root<Advertisement> from = cq.from(clazz);
        cq.select(from);
        return cq.where(cb.equal(from.get(parentName).get("id"), parentId));
    }
}
