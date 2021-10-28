package org.project.dto.mapper;

public interface DtoMapper<T, R> {
    R mapToDto(T entity);
    T mapToEntity(R dto);
}
