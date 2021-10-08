package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Category;
import org.project.repository.CategoryRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl extends CrudRepositoryGeneral<Category> implements CategoryRepository {

    @Autowired
    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
