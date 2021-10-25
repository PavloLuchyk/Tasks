package org.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.project.configuration.ApplicationConfig;
import org.project.configuration.WebConfig;
import org.project.model.Category;
import org.project.repository.CrudRepository;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ApplicationConfig.class,
        WebConfig.class
})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CrudRepositoryGeneralTests {

    @Autowired
    private CrudRepository<Category> categoryRepository;

    @Test
    public void readByIdTest() {
        Category expected = new Category();
        expected.setName("Books");
        expected.setDescription("There are books");
        Category actual = categoryRepository.readById(Long.parseLong(categoryRepository.create(expected).toString()));
        expected.setCreateDate(expected.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        actual.setCreateDate(actual.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(expected, actual);
    }

    @Test
    public void createTest() {
        Category expected = new Category();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setName("Books");
        expected.setDescription("There are books");
        Long id = Long.parseLong(categoryRepository.create(expected).toString());
        assertEquals(5, id);
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
    }

    @Test
    void deleteTest() {
        long id = Long.parseLong(categoryRepository.create(new Category().setName("Name").setDescription("desc")).toString());
        categoryRepository.delete(categoryRepository.readById(id));
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
        category.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.add(category);
        category = new Category();
        category.setName("Books1");
        category.setDescription("There are books2");
        categoryRepository.create(category);
        category.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.add(category);
        List<Category> actual = categoryRepository.readAll();
        actual.forEach(category1 -> category1.setCreateDate(category1.getCreateDate().truncatedTo(ChronoUnit.SECONDS)));
        assertEquals(expected, actual);
    }

    @Test
    public void readAllSorted() {
        List<Category> expected = new ArrayList<>();
        Category category1 = new Category().setName("Name1").setDescription("S");
        categoryRepository.create(category1);
        category1.setCreateDate(category1.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        Category category2 = new Category().setName("Name2").setDescription("S");
        categoryRepository.create(category2);
        category2.setCreateDate(category1.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        expected.add(category2);
        expected.add(category1);
        List<Category> actual = categoryRepository.readAllSorted(SortingOrder.DESC);
        actual.forEach(category -> category.setCreateDate(category.getCreateDate().truncatedTo(ChronoUnit.SECONDS)));
        assertEquals(expected, actual);
    }

    @Test
    public void getAllInPagesTest() {
        long[] ids = new long[31];
        for (int i = 0; i< 31; i++) {
           ids[i] = Long.parseLong(categoryRepository.create(new Category().setName("Name"+i).setDescription("s")).toString());
        }
        long expected = 3;
        long actual = categoryRepository.getAllInPages(PageSize.SIZE15).size();
        assertEquals(expected, actual);

    }
}
