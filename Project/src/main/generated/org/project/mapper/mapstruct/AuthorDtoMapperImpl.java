package org.project.mapper.mapstruct;

import javax.annotation.processing.Generated;
import org.project.dto.AuthorDto;
import org.project.model.Author;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-01T14:43:16+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15 (Oracle Corporation)"
)
@Component
public class AuthorDtoMapperImpl implements AuthorDtoMapper {

    @Override
    public AuthorDto toAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId( author.getId() );
        authorDto.setFirstName( author.getFirstName() );
        authorDto.setLastName( author.getLastName() );
        authorDto.setEmail( author.getEmail() );
        authorDto.setPassword( author.getPassword() );
        authorDto.setCreateDate( author.getCreateDate() );
        authorDto.setRole( author.getRole() );

        return authorDto;
    }

    @Override
    public Author toAuthorEntity(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( authorDto.getId() );
        author.setFirstName( authorDto.getFirstName() );
        author.setLastName( authorDto.getLastName() );
        author.setCreateDate( authorDto.getCreateDate() );
        author.setEmail( authorDto.getEmail() );
        author.setPassword( authorDto.getPassword() );
        author.setRole( authorDto.getRole() );

        return author;
    }
}
