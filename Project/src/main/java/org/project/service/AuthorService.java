package org.project.service;

import org.project.model.Author;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthorService extends CrudService<Author> {
    @Transactional
    Author getByEmail(String email);

    boolean checkUnique(String email);

    boolean isIdentical(String password, long id);
}
