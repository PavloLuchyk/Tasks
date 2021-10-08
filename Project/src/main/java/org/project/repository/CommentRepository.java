package org.project.repository;

import org.project.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment>, ParentRepository<Comment> {

}
