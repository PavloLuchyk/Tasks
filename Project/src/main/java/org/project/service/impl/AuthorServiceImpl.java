package org.project.service.impl;

import org.project.model.Author;
import org.project.model.Role;
import org.project.repository.AuthorRepository;
import org.project.service.AuthorService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl extends CrudServiceGeneral<Author> implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             @Qualifier("bCryptPasswordEncoder") BCryptPasswordEncoder passwordEncoder) {
        super(authorRepository);
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Author create(Author element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        element.setPassword(passwordEncoder.encode(element.getPassword()));
        element.setRole(Role.USER);
        return element.setId(Long.parseLong(authorRepository.create(element).toString()));
    }

    @Override
    @Transactional
    public Author getByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return authorRepository.getByEmail(email);
    }

    @Override
    public boolean checkUnique(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return authorRepository.checkUniqueByEmail(email);
    }
}
