package org.project.security;

import org.project.model.Author;
import org.project.repository.AuthorRepository;
import org.project.security.jwt.JwtUser;
import org.project.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private AuthorRepository authorRepository;

    @Autowired
    public JwtUserDetailsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.getByEmail(username);
        if (author == null){
            throw new IllegalArgumentException("User with such email not found");
        }
        JwtUser jwtUser = JwtUserFactory.getJwtUser(author);
        return jwtUser;
    }
}
