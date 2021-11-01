package org.project.mapper;

public interface SimpleDtoEntityMapper<T, R> extends DtoMapper<T, R> {
    T mapToEntity(R dto);
}
