package org.project.mapper;

public interface DtoMapper<T, R>{
    R mapToDto(T entity);
}
