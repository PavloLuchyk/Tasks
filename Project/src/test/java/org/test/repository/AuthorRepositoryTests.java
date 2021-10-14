package org.test.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project.configuration.ApplicationConfig;
import org.project.model.Author;
import org.project.model.Role;
import org.project.repository.AdvertisementRepository;
import org.project.repository.AuthorRepository;
import org.project.repository.CategoryRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.temporal.ChronoUnit;

public class AuthorRepositoryTests {

    private static AuthorRepository authorRepository;

    @BeforeAll
    public static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        authorRepository = applicationContext.getBean(AuthorRepository.class);
    }

    @Test
    public void getByEmailTest() {
        Author expected = new Author();
        expected.setFirstName("Thomas");
        expected.setLastName("Edison");
        expected.setEmail("edison@gmail.com");
        expected.setPassword("Ed1234");
        expected.setRole(Role.USER);
        authorRepository.create(expected);
        Author actual = authorRepository.getByEmail(expected.getEmail());
        expected.setCreateDate(expected.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        actual.setCreateDate(actual.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(expected, actual);
    }
}
