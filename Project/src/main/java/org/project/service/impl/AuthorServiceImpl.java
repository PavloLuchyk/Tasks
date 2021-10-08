package org.project.service.impl;

import org.project.model.Author;
import org.project.repository.AuthorRepository;
import org.project.service.AuthorService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl extends CrudServiceGeneral<Author> implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public Author create(Author element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return element.setId(Long.parseLong(authorRepository.create(element).toString()));
    }
}
