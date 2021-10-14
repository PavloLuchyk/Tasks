package org.project.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtUser implements UserDetails {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDateTime createDate;

    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    public JwtUser(long id,
                   String firstName,
                   String lastName,
                   String email,
                   String password,
                   LocalDateTime createDate,
                   Collection<? extends GrantedAuthority> grantedAuthorities
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.grantedAuthorities = grantedAuthorities;
    }

    public long getId() {
        return id;
    }

    public JwtUser setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public JwtUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public JwtUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JwtUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public JwtUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public JwtUser setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
