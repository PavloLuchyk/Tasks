package org.test.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project.configuration.ApplicationConfig;
import org.project.model.Advertisement;
import org.project.model.Author;
import org.project.model.Category;
import org.project.model.Comment;
import org.project.repository.AdvertisementRepository;
import org.project.repository.AuthorRepository;
import org.project.repository.CategoryRepository;
import org.project.repository.CommentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentRepositoryTests {

    private static AuthorRepository authorRepository;
    private static AdvertisementRepository advertisementRepository;
    private static CommentRepository commentRepository;

    @BeforeAll
    public static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        authorRepository = applicationContext.getBean(AuthorRepository.class);
        advertisementRepository = applicationContext.getBean(AdvertisementRepository.class);
        commentRepository = applicationContext.getBean(CommentRepository.class);
    }

    @Test
    public void getAllByParentId() {
        List<Comment> comments = new ArrayList<>();
        Author author = new Author();
        author.setId(Long.parseLong(authorRepository.create(author).toString()));
        Comment comment = new Comment();
        comment.setAuthor(author);
        for (int i = 0; i < 3; i++) {
            commentRepository.create(comment);
            comments.add(comment);
        }
        Author author2 = new Author();
        author2.setId(Long.parseLong(authorRepository.create(author2).toString()));
        commentRepository.create(new Comment().setAuthor(author2));
        assertEquals(comments, commentRepository.getAllByParentId(author.getId(), "author"));
    }

    @Test
    public void getAllByCategoryId() {
        List<Comment> comments = new ArrayList<>();
        Advertisement advertisement = new Advertisement();
        advertisement.setId(Long.parseLong(advertisementRepository.create(advertisement).toString()));
        Comment comment = new Comment();
        comment.setAdvertisement(advertisement);
        for (int i = 0; i < 3; i++) {
            commentRepository.create(comment);
            comments.add(comment);
        }
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setId(Long.parseLong(advertisementRepository.create(advertisement1).toString()));
        commentRepository.create(new Comment().setAdvertisement(advertisement1));
        assertEquals(comments, commentRepository.getAllByParentId(advertisement.getId(),"advertisement"));
    }

}
