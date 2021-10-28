package org.project.repository;

import org.project.enums.PageSize;

import java.util.List;

public interface ParentRepository<T> {
    List<T> getAllByParentId(long parentId, String parentName);
    List<T> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber);
    Long getTotalCountOfPages(long parentId, String parentName, PageSize pageSize);
}
