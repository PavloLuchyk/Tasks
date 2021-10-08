package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Advertisement;
import org.project.repository.AdvertisementRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        CriteriaBuilder cb =  sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Advertisement> cq = cb.createQuery(clazz);
        Root<Advertisement> from = cq.from(clazz);
        cq.select(from);
        CriteriaQuery<Advertisement> query = cq.where(cb.equal(from.get(parentName).get("id"), parentId));
        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
    }
}
