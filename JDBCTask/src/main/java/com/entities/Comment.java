package com.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Comment {
    private long id;

    private LocalDateTime creationDate;

    private String text;

    private User owner;

    private Advertisement advertisement;

    public Comment(long id, LocalDateTime creationDate, String text, User owner, Advertisement advertisement) {
        this.id = id;
        this.creationDate = creationDate;
        this.text = text;
        this.owner = owner;
        this.advertisement = advertisement;
    }

    public Comment() {

    }

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Comment setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Comment setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Comment setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(creationDate, comment.creationDate) && Objects.equals(text, comment.text) && Objects.equals(owner, comment.owner) && Objects.equals(advertisement, comment.advertisement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, text, owner, advertisement);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Comment{");
        sb.append("id=").append(id);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", text='").append(text).append('\'');
        sb.append(", owner=").append(owner);
        sb.append('}');
        return sb.toString();
    }

    public String toStringWithAdvertisement(){
        final StringBuffer sb = new StringBuffer("Comment{");
        sb.append("id=").append(id);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", text='").append(text).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", advertisement=").append(advertisement);
        sb.append('}');
        return sb.toString();
    }
}
