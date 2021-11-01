package org.project.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "advertisements")
public class Advertisement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Category category;

    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne
    private Author author;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createDate desc")
    @JsonIgnore
    private List<Comment> comments;

    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createDate;

    public Advertisement() {
    }

    public long getId() {
        return id;
    }

    public Advertisement setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Advertisement setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Advertisement setDescription(String description) {
        this.description = description;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Advertisement setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Advertisement setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Advertisement setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Advertisement setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(author, that.author) && Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, category, author, createDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Advertisement{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", category=").append(category);
        sb.append(", author=").append(author);
       // sb.append(", comments=").append(comments);
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }
}
