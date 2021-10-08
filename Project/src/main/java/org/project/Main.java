package org.project;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.project.configuration.ApplicationConfig;
import org.project.model.Advertisement;
import org.project.model.Category;
import org.project.model.Comment;
import org.project.repository.AdvertisementRepository;
import org.project.repository.CategoryRepository;
import org.project.repository.CommentRepository;
import org.project.repository.impl.CategoryRepositoryImpl;
import org.project.util.PageSize;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.cre();
    }

    public void cre() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentRepository commentRepository = applicationContext.getBean(CommentRepository.class);
        Configuration configuration = new Configuration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(sessionFactory);
        categoryRepository.create(new Category());
        commentRepository.create(new Comment().setText("aaa"));
        /*AdvertisementRepository advertisementRepository = applicationContext.getBean(AdvertisementRepository.class);
        for (int i = 0; i< 31; i++) {
            advertisementRepository.create(new Advertisement());
        }

        System.out.println(advertisementRepository.getAllInPages(PageSize.SIZE15));*/
    }
}
