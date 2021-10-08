package org.project.repository.impl;

import org.hibernate.SessionFactory;
import org.project.model.Author;
import org.project.repository.AuthorRepository;
import org.project.repository.CrudRepositoryGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;


@Transactional
@Repository
public class AuthorRepositoryImpl extends CrudRepositoryGeneral<Author> implements AuthorRepository {

    @Autowired
    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
