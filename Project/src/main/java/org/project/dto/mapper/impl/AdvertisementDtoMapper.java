package org.project.dto.mapper.impl;

import org.project.dto.AdvertisementDto;
import org.project.dto.mapper.DtoMapper;
import org.project.model.Advertisement;
import org.project.model.Author;
import org.project.model.Category;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementDtoMapper implements DtoMapper<Advertisement, AdvertisementDto> {

    public Advertisement mapToEntity(AdvertisementDto dto, Category relationEntity1, Author relationEntity2) {
        return new Advertisement()
                .setAuthor(relationEntity2)
                .setCategory(relationEntity1)
                .setId(dto.getId())
                .setTitle(dto.getTitle())
                .setDescription(dto.getDescription())
                .setCreateDate(dto.getCreateDate());
    }

    @Override
    public AdvertisementDto mapToDto(Advertisement entity) {
        return new AdvertisementDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCategory() == null ? 0: entity.getCategory().getId(),
                entity.getCategory() == null ? null : entity.getCategory().getName(),
                entity.getAuthor().getId(),
                entity.getCreateDate()
        );
    }
}