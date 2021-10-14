package org.project.security.jwt;

import org.project.model.Author;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {
    public static JwtUser getJwtUser(Author author) {
        return new JwtUser(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getEmail(),
                author.getPassword(),
                author.getCreateDate(),
                getAuthorAuthorities(author)
        );
    }

    private static List<GrantedAuthority> getAuthorAuthorities(Author author) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(author.getRole().toString()));
        return grantedAuthorities;
    }
}
