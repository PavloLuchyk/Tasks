package org.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDto {
    private long id;

    @NotBlank(message = "The 'text' cannot be empty")
    private String text;

    @NotNull
    private long advertisementId;

    @NotNull
    private long authorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate;

    public CommentDto(long id, String text, long advertisementId, long authorId, LocalDateTime createDate) {
        this.id = id;
        this.text = text;
        this.advertisementId = advertisementId;
        this.authorId = authorId;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return advertisementId == that.advertisementId && authorId == that.authorId && Objects.equals(text, that.text) && Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, advertisementId, authorId, createDate);
    }
}
