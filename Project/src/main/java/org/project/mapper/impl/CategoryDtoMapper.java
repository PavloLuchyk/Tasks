package org.project.mapper.impl;

import org.project.dto.CategoryDto;
import org.project.mapper.SimpleDtoEntityMapper;
import org.project.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapper implements SimpleDtoEntityMapper<Category, CategoryDto> {
    @Override
    public CategoryDto mapToDto(Category entity) {
        return new CategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreateDate()
        );
    }

    @Override
    public Category mapToEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setCreateDate(dto.getCreateDate());
        return category;
    }
}
