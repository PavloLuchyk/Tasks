package org.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.model.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;

public class AuthorDto {
    private long id;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    private Role role;

    public AuthorDto(long id, String firstName, String lastName, String email, String password, LocalDateTime createDate, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(firstName, authorDto.firstName) && Objects.equals(lastName, authorDto.lastName) && Objects.equals(email, authorDto.email) && Objects.equals(password, authorDto.password) && Objects.equals(createDate, authorDto.createDate) && role == authorDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, createDate, role);
    }
}
