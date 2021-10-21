package org.project.repository;

import org.project.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author> {
    Author getByEmail(String email);
    boolean checkUniqueByEmail(String email);
}
