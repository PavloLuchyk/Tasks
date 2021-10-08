package org.project.repository;

import org.project.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category> {
}
