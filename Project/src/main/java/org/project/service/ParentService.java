package org.project.service;

import java.util.List;

public interface ParentService<T> {

    List<T> getAllByParentId(long parentId, String parentName);
}
