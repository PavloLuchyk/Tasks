package org.project.controller;

import org.project.model.Comment;
import org.project.service.CommentService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class CommentController {
    
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Comment> insert(@RequestBody Comment comment) {
        comment = commentService.create(comment);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getById(@PathVariable long id) {
        Comment comment = commentService.readById(id);
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/comment/sorted/{order}")
    public ResponseEntity<List<Comment>> getAllSorted(@PathVariable String order) {
        List<Comment> comments = commentService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()));
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/pages/{number}")
    public ResponseEntity<Map<Integer, List<Comment>>> getAllInPages(@PathVariable int number) {
        Map<Integer, List<Comment>> pages = commentService.getAllInPages(PageSize.getFromSize(number));
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getAll(){
        List<Comment> comments = commentService.readAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{parent}/{id}")
    public ResponseEntity<List<Comment>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<Comment> comments = commentService.getAllByParentId(id, parent);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/comment/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @commentController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Comment> update(@PathVariable("id") long id, @RequestBody Comment comment) {
        comment.setId(id);
        comment = commentService.update(comment);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comment/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @commentController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        commentService.delete(new Comment().setId(id));
        return ResponseEntity.ok("comment with id " + id + " has been deleted");
    }

    public boolean isOwner(long commentId, long id) {
        Comment current = commentService.readById(commentId);
        return id == current.getAuthor().getId()
                || id == current.getAdvertisement().getAuthor().getId();
    }
}
