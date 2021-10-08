package org.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.model.Comment;
import org.project.repository.CommentRepository;
import org.project.service.impl.CommentServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentServiceTests {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTest() {
        Comment expected = new Comment();
        Mockito.when(commentRepository.create(expected)).thenReturn(1L);
        Comment actual = commentService.create(expected);
        assertEquals(expected, actual);
        Mockito.verify(commentRepository, Mockito.times(1)).create(expected);
    }

    @Test
    public void createTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> commentService.create(null));
    }

    @Test
    public void getByParentId() {
        List<Comment> expected = List.of(new Comment(), new Comment());
        Mockito.when(commentRepository.getAllByParentId(1, "author")).thenReturn(expected);
        List<Comment> actual = commentService.getAllByParentId(1, "author");
        assertEquals(expected, actual);
        Mockito.verify(commentRepository, Mockito.times(1)).getAllByParentId(1, "author");
    }
}
