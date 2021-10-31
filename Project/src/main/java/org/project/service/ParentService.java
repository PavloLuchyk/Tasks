package org.project.service;

import org.project.enums.PageSize;

import java.util.List;

public interface ParentService<T> {

    List<T> getAllByParentId(long parentId, String parentName);
    List<T> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber);
    Number getTotalCountOfPages(long parentId, String parentName, PageSize pageSize);
}
