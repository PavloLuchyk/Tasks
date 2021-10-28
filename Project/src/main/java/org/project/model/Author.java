package org.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;
import org.project.util.serialization.custom.LocalDateTimeDeserializer;
import org.project.util.serialization.custom.LocalDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Pattern(regexp = "^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(name = "create_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize( using = LocalDateTimeDeserializer.class)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Advertisement> advertisements;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    public Author() {
    }

    public long getId() {
        return id;
    }

    public Author setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Author setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Author setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Author setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Author setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Author setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public Author setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Author setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Author setRole(Role role) {
        this.role = role;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(email, author.email) && Objects.equals(password, author.password) && Objects.equals(createDate, author.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, createDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Author{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", createDate=").append(createDate);
       // sb.append(", advertisements=").append(advertisements);
      //  sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }
}
