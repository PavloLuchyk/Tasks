package org.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project.configuration.ApplicationConfig;
import org.project.model.Advertisement;
import org.project.model.Author;
import org.project.model.Category;
import org.project.repository.AdvertisementRepository;
import org.project.repository.AuthorRepository;
import org.project.repository.CategoryRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementRepositoryTests {

    private static CategoryRepository categoryRepository;
    private static AuthorRepository authorRepository;
    private static AdvertisementRepository advertisementRepository;

    @BeforeAll
    public static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        categoryRepository = applicationContext.getBean(CategoryRepository.class);
        authorRepository = applicationContext.getBean(AuthorRepository.class);
        advertisementRepository = applicationContext.getBean(AdvertisementRepository.class);
    }

    @Test
    public void getAllByAuthorId() {
        List<Advertisement> advertisements = new ArrayList<>();
        Author author = new Author();
        author.setId(Long.parseLong(authorRepository.create(author).toString()));
        Advertisement advertisement = new Advertisement();
        advertisement.setAuthor(author);
        for (int i = 0; i < 3; i++) {
            advertisementRepository.create(advertisement);
            advertisements.add(advertisement);
        }
        Author author2 = new Author();
        author2.setId(Long.parseLong(authorRepository.create(author2).toString()));
        advertisementRepository.create(new Advertisement().setAuthor(author2));
        assertEquals(advertisements, advertisementRepository.getAllByParentId(author.getId(), "author"));
    }

    @Test
    public void getAllByCategoryId() {
        List<Advertisement> advertisements = new ArrayList<>();
        Category category = new Category();
        category.setId(Long.parseLong(categoryRepository.create(category).toString()));
        Advertisement advertisement = new Advertisement();
        advertisement.setCategory(category);
        for (int i = 0; i < 3; i++) {
            advertisementRepository.create(advertisement);
            advertisements.add(advertisement);
        }
        Category category1 = new Category();
        category1.setId(Long.parseLong(categoryRepository.create(category1).toString()));
        advertisementRepository.create(new Advertisement().setCategory(category1));
        assertEquals(advertisements, advertisementRepository.getAllByParentId(category.getId(), "category"));
    }
}
