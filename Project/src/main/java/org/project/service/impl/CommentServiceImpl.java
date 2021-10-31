package org.project.service.impl;

import org.project.enums.PageSize;
import org.project.model.Comment;
import org.project.repository.CommentRepository;
import org.project.service.CommentService;
import org.project.service.CrudServiceGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl extends CrudServiceGeneral<Comment> implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl( CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllByParentId(long parentId, String parentName) {
        return commentRepository.getAllByParentId(parentId, parentName);
    }

    @Override
    public List<Comment> getAllByParentIdInPages(long parentId, String parentName, PageSize pageSize, int pageNumber) {
        return commentRepository.getAllByParentIdInPages(parentId, parentName, pageSize, pageNumber);
    }

    @Override
    public Number getTotalCountOfPages(long parentId, String parentName, PageSize pageSize) {
        return commentRepository.getTotalCountOfPages(parentId,parentName,pageSize);
    }
}
