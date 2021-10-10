package org.project.controller;

import org.project.model.Author;
import org.project.model.Category;
import org.project.service.AuthorService;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @PostMapping("/author/add")
    public ResponseEntity<?> insert(@RequestBody Author author) {
        System.out.println("Json " + author);
        author = authorService.create(author);
        return ResponseEntity.ok().body("New author" + author.getId());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getById(@PathVariable long id) {
        Author author = authorService.readById(id);
        return ResponseEntity.ok().body(author);
    }

    @GetMapping("/author/sorted/{order}")
    public ResponseEntity<List<Author>> getAllSorted(@PathVariable String order) {
        List<Author> authors = authorService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()));
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/author/pages/{number}")
    public ResponseEntity<Map<Integer, List<Author>>> getAllInPages(@PathVariable int number) {
        Map<Integer, List<Author>> pages = authorService.getAllInPages(PageSize.getFromSize(number));
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Author>> getAll(){
        List<Author> authors = authorService.readAll();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Author author) {
        author.setId(id);
        authorService.update(author);
        return ResponseEntity.ok("Author updated successfully " + author.getId());
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        authorService.delete(new Author().setId(id));
        return ResponseEntity.ok("Author with id " + id + " has been deleted");
    }
}
