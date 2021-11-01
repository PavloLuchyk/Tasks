package org.project.mapper.impl;

import org.project.dto.AuthorDto;
import org.project.mapper.SimpleDtoEntityMapper;
import org.project.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorDtoMapper implements SimpleDtoEntityMapper<Author, AuthorDto> {

    @Override
    public AuthorDto mapToDto(Author entity) {
        return new AuthorDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreateDate(),
                entity.getRole()
        );
    }

    @Override
    public Author mapToEntity(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword())
                .setCreateDate(dto.getCreateDate())
                .setRole(dto.getRole());
        return author;
    }
}
