package org.project.controller;

import org.project.model.Comment;
import org.project.service.CommentService;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/add")
    public ResponseEntity<?> insert(@RequestBody Comment comment) {
        System.out.println("Json " + comment);
        comment = commentService.create(comment);
        return ResponseEntity.ok().body("New comment" + comment.getId());
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
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Comment comment) {
        comment.setId(id);
        commentService.update(comment);
        return ResponseEntity.ok("comment updated successfully " + comment.getId());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        commentService.delete(new Comment().setId(id));
        return ResponseEntity.ok("comment with id " + id + " has been deleted");
    }
}
