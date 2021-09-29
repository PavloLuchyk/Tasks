package com.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Advertisement {
     private long id;

     private String title;

     private LocalDateTime creationDate;

     private String description;

     private User owner;

     private List<Comment> comments;

    public Advertisement(long id, String title, LocalDateTime creationDate, String description, User owner) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.description = description;
        this.owner = owner;
    }

    public Advertisement() {

    }


    public Advertisement(long id) {
        this.id = id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Advertisement setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Advertisement setDescription(String description) {
        this.description = description;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Advertisement setOwner(User owner) {
        this.owner = owner;
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
        return Objects.equals(title, that.title) && Objects.equals(creationDate, that.creationDate) && Objects.equals(description, that.description) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, creationDate, description, owner);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Advertisement{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }
}
