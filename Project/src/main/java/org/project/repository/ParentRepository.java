package org.project.repository;

import java.util.List;

public interface ParentRepository<T> {
    List<T> getAllByParentId(long parentId, String parentName);
}
