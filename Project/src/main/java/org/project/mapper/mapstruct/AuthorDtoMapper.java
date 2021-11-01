package org.project.mapper.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.project.dto.AuthorDto;
import org.project.model.Author;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorDtoMapper {

     AuthorDtoMapper INSTANCE = Mappers.getMapper(AuthorDtoMapper.class);

     AuthorDto toAuthorDto(Author author);

     Author toAuthorEntity(AuthorDto authorDto);
}
