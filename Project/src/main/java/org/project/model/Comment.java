package org.project.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition="TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    private Advertisement advertisement;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Comment setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Comment setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Comment setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text) && Objects.equals(advertisement, comment.advertisement) && Objects.equals(author, comment.author) && Objects.equals(createDate, comment.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, advertisement, author, createDate);
    }
}
