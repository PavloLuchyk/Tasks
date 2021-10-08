package org.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.model.Author;
import org.project.repository.AuthorRepository;
import org.project.service.impl.AuthorServiceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTest() {
        Author expected = new Author();
        expected.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        expected.setFirstName("Author");
        Mockito.when(authorRepository.create(expected)).thenReturn(1L);
        expected.setId(1L);
        Author actual = authorService.create(expected);
        assertEquals(expected, actual);
        Mockito.verify(authorRepository, Mockito.times(1)).create(expected);
    }

    @Test
    public void createNullTest() {
        assertThrows(IllegalArgumentException.class, () -> authorService.create(null));
    }

}
