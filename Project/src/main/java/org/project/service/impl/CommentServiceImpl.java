package org.project.service.impl;

import org.project.model.Comment;
import org.project.repository.CommentRepository;
import org.project.service.CommentService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl extends CrudServiceGeneral<Comment> implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl( CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Comment create(Comment element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null!");
        }
        return element.setId(Long.parseLong(commentRepository.create(element).toString()));
    }

    @Override
    public List<Comment> getAllByParentId(long parentId, String parentName) {
        return commentRepository.getAllByParentId(parentId, parentName);
    }
}
