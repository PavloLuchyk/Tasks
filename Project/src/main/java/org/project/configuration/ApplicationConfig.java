package org.project.configuration;

import static org.hibernate.cfg.Environment.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = {@ComponentScan("org.project.repository"), @ComponentScan("org.project.service"),
        @ComponentScan("org.project.configuration")})
@PropertySource("classpath:hibernate.properties")
public class ApplicationConfig {

    private Environment environment;

    @Autowired
    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        Properties props = new Properties();
        props.put(DRIVER, environment.getProperty("hibernate.connection.driver_class"));
        props.put(URL, environment.getProperty("hibernate.connection.url"));
        props.put(USER, environment.getProperty("hibernate.connection.username"));
        props.put(PASS, environment.getProperty("hibernate.connection.password"));
        props.put(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        props.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT,
                environment.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));
        factoryBean.setHibernateProperties(props);
        factoryBean.setPackagesToScan("org.project.model");
        return factoryBean;
    }


    @Bean
    public TransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
