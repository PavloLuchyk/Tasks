package org.project.configuration;

import org.project.security.jwt.JwtTokenFilter;
import org.project.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;


public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /*@Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new JwtTokenFilter(jwtTokenProvider) };
    }*/
}

