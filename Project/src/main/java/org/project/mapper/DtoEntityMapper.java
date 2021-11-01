package org.project.mapper;

public interface DtoEntityMapper<T, R, U, A> extends DtoMapper<T,R> {
    T mapToEntity(R dto, U relationEntity1, A relationEntity2);
}
