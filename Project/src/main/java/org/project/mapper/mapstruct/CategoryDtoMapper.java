package org.project.mapper.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.project.dto.CategoryDto;
import org.project.model.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryDtoMapper {

   CategoryDtoMapper toCategoryDto(Category category);

   Category toCategoryEntity(CategoryDto categoryDto);
}
