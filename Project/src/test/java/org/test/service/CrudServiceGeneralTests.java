package org.test.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.model.Category;
import org.project.repository.CategoryRepository;
import org.project.service.impl.CategoryServiceImpl;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrudServiceGeneralTests {

    @Mock
    private CategoryRepository crudRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category expected;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        expected = new Category();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setName("Books");
        expected.setDescription("There are books");
    }

    @AfterEach
    public void dInit() {
        expected = null;
    }

    @Test
    public void readByIdTest() {
        long id = 1;
        Mockito.when(crudRepository.readById(id)).thenReturn(expected);
        Category actual = categoryService.readById(id);
        assertEquals(expected, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).readById(id);
    }

    @Test
    public void readByIdNegative() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.readById(-1));
    }

    @Test
    public void createTest() {
        Mockito.when(crudRepository.create(expected)).thenReturn(1);
        Category actual = categoryService.create(expected);
        assertEquals(expected, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).create(expected);
    }

    @Test
    public void createTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.create(null));
    }

    @Test
    public void updateTest() {
        expected.setName("Books2");
        Mockito.when(crudRepository.update(expected)).thenReturn(expected);
        Category actual = categoryService.update(expected);
        assertEquals(expected, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).update(expected);
    }

    @Test
    public void updateTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.update(null));
    }

    @Test
    void deleteTest() {
        Mockito.when(crudRepository.readById(1)).thenReturn(null);
        categoryService.delete(new Category().setId(1));
        assertNull(categoryService.readById(1));

    }

    @Test
    public void deleteTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.delete(null));
    }

    @Test
    public void readAllTest() {
        List<Category> categories = List.of(new Category(), new Category());
        Mockito.when(crudRepository.readAll()).thenReturn(List.of(new Category(), new Category()));
        List<Category> actual = categoryService.readAll();
        assertEquals(categories, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).readAll();
    }

    @Test
    public void readAllSortedTest() {
        Category category1 = new Category();
        category1.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Category category2 = new Category();
        category2.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1));
        List<Category> expected = List.of(category2, category1);
        Mockito.when(crudRepository.readAllSorted(SortingOrder.DESC)).thenReturn(List.of(category2, category1));
        List<Category> actual = categoryService.readAllSorted(SortingOrder.DESC);
        assertEquals(expected, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).readAllSorted(SortingOrder.DESC);
    }



    @Test
    public void getAllInPagesTest() {
        long expected = 3;
        Map<Integer, List<Category>> map = new TreeMap<>();
        map.put(1, new ArrayList<>());
        map.put(2, new ArrayList<>());
        map.put(3, new ArrayList<>());
        Mockito.when(crudRepository.getAllInPages(PageSize.SIZE15)).thenReturn(map);
        long actual = categoryService.getAllInPages(PageSize.SIZE15).size();
        assertEquals(expected, actual);
        Mockito.verify(crudRepository, Mockito.times(1)).getAllInPages(PageSize.SIZE15);
    }

    @Test
    public void getAllInPagesTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.getAllInPages(null));
    }
}
