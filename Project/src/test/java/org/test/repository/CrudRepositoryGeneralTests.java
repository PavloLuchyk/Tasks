package org.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project.configuration.ApplicationConfig;
import org.project.model.Category;
import org.project.repository.CategoryRepository;
import org.project.repository.CrudRepository;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CrudRepositoryGeneralTests {

    private static CrudRepository<Category> categoryRepository;

    @BeforeAll
    public static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        categoryRepository = applicationContext.getBean(CategoryRepository.class);
    }

    @Test
    public void readByIdTest() {
        Category expected = new Category();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setName("Books");
        expected.setDescription("There are books");
        Category actual = categoryRepository.readById(Long.parseLong(categoryRepository.create(expected).toString()));
        assertEquals(expected, actual);
        categoryRepository.delete(actual);
    }

    @Test
    public void createTest() {
        Category expected = new Category();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setName("Books");
        expected.setDescription("There are books");
        Long id = Long.parseLong(categoryRepository.create(expected).toString());
        assertEquals(5, id);
        categoryRepository.delete(expected.setId(id));
    }

    @Test
    public void updateTest() {
        Category expected = new Category();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setName("Books");
        expected.setDescription("There are books");
        expected.setId(Long.parseLong(categoryRepository.create(expected).toString()));
        expected.setName("Books2");
        Category actual = categoryRepository.update(expected);
        assertEquals(expected, actual);
        categoryRepository.delete(actual);
    }

    @Test
    void deleteTest() {
        long id = Long.parseLong(categoryRepository.create(new Category()).toString());
        categoryRepository.delete(new Category().setId(id));
        assertNull(categoryRepository.readById(id));
    }

    @Test
    public void readAllTest() {
        List<Category> expected = new ArrayList<>();
        Category category = new Category();
        category.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        category.setName("Books");
        category.setDescription("There are books");
        categoryRepository.create(category);
        expected.add(category);
        category = new Category();
        category.setDescription("There are books2");
        categoryRepository.create(category);
        expected.add(category);
        List<Category> actual = categoryRepository.readAll();
        assertEquals(expected, actual);
        for (Category c: actual) {
            categoryRepository.delete(c);
        }
    }

    @Test
    public void readAllSorted() {
        List<Category> expected = new ArrayList<>();
        Category category1 = new Category();
        category1.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        categoryRepository.create(category1);
        Category category2 = new Category();
        category2.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1));
        categoryRepository.create(category2);
        expected.add(category2);
        expected.add(category1);
        List<Category> actual = categoryRepository.readAllSorted(SortingOrder.DESC);
        assertEquals(expected, actual);
        for (Category c: actual) {
            categoryRepository.delete(c);
        }
    }

    @Test
    public void getAllInPagesTest() {
        long[] ids = new long[31];
        for (int i = 0; i< 31; i++) {
           ids[i] = Long.parseLong(categoryRepository.create(new Category()).toString());
        }
        long expected = 3;
        long actual = categoryRepository.getAllInPages(PageSize.SIZE15).size();
        assertEquals(expected, actual);
        for (int i = 0; i< 31; i++) {
            categoryRepository.delete(new Category().setId(ids[i]));
        }
    }
}
