package org.project.mapper.impl;

import org.project.dto.CommentDto;
import org.project.mapper.DtoMapper;
import org.project.model.Advertisement;
import org.project.model.Author;
import org.project.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {

    public Comment mapToEntity(CommentDto dto, Advertisement advertisement, Author author) {
        return new Comment()
                .setId(dto.getId())
                .setText(dto.getText())
                .setCreateDate(dto.getCreateDate())
                .setAuthor(author)
                .setAdvertisement(advertisement);
    }

    @Override
    public CommentDto mapToDto(Comment entity) {
        return new CommentDto(
                entity.getId(),
                entity.getText(),
                entity.getAdvertisement().getId(),
                entity.getAuthor().getId(),
                entity.getAuthor().getFirstName() + " " + entity.getAuthor().getLastName(),
                entity.getCreateDate()
        );
    }
}
