package org.project.service.impl;

import org.project.model.Advertisement;
import org.project.model.Category;
import org.project.repository.AdvertisementRepository;
import org.project.repository.CategoryRepository;
import org.project.service.CategoryService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends CrudServiceGeneral<Category> implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               AdvertisementRepository advertisementRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.advertisementRepository = advertisementRepository;
    }


    @Transactional
    @Override
    public Category create(Category element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return element.setId(Long.parseLong(categoryRepository.create(element).toString()));
    }

    @Transactional
    public Category setAllAdvertisementsToNull(Category element) {
        Category category = null;
        for(Advertisement advertisement: advertisementRepository.getAllByParentId(element.getId(), "category")) {
             category = advertisement.getCategory();
            advertisementRepository.update(advertisement.setCategory(null));
        }
        return category == null ? element : category ;
    }

    @Override
    public void delete(Category element) {
        deleteElement(setAllAdvertisementsToNull(element));
    }

    @Transactional
    public void deleteElement(Category element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        categoryRepository.delete(element);
    }

    @Override
    public boolean checkUnique(String name) {
        return categoryRepository.checkUniqueName(name);
    }
}
