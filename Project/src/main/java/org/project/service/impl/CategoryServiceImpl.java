package org.project.service.impl;

import org.project.model.Category;
import org.project.repository.CategoryRepository;
import org.project.service.CategoryService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryServiceImpl extends CrudServiceGeneral<Category> implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    @Override
    public Category create(Category element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return element.setId(Long.parseLong(categoryRepository.create(element).toString()));
    }
}
