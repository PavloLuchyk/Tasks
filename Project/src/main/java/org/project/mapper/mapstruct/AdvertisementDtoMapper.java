package org.project.mapper.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.project.dto.AdvertisementDto;
import org.project.model.Advertisement;
import org.project.service.AuthorService;
import org.project.service.CategoryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AdvertisementDtoMapper {

    private final AuthorService authorService;
    private final CategoryService categoryService;

    public AdvertisementDtoMapper(AuthorService authorService, CategoryService categoryService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Mappings({
            @Mapping(target = "authorId", source = "advertisement.author.id"),
            @Mapping(target = "categoryId", source = "advertisement.category.id")
    })
    public abstract AdvertisementDto toAdvertisementDto(Advertisement advertisement);
}
