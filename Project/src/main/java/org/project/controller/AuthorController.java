package org.project.controller;

import org.project.model.Author;
import org.project.service.AuthorService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/author/add")
    public ResponseEntity<Author> insert(@RequestBody Author author) {
        author = authorService.create(author);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getById(@PathVariable long id) {
        Author author = authorService.readById(id);
        return ResponseEntity.ok().body(author);
    }

    @GetMapping("/author/email/{email}")
    public ResponseEntity<Author> getById(@PathVariable String email) {
        Author author = authorService.getByEmail(email);
        return ResponseEntity.ok().body(author);
    }

    @GetMapping("/author/sorted/{order}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Author>> getAllSorted(@PathVariable String order) {
        List<Author> authors = authorService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()));
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/author/pages/{number}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<Integer, List<Author>>> getAllInPages(@PathVariable int number) {
        Map<Integer, List<Author>> pages = authorService.getAllInPages(PageSize.getFromSize(number));
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/author")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Author>> getAll(){
        List<Author> authors = authorService.readAll();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/author/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Author> update(@PathVariable("id") long id, @RequestBody Author author) {
        author.setId(id);
        author = authorService.update(author);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/author/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        authorService.delete(authorService.readById(id));
        return ResponseEntity.ok("Author with id " + id + " has been deleted");
    }

    @GetMapping("/author/check")
    public ResponseEntity<?> checkUnique(@RequestParam("email") String name) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("unique", authorService.checkUnique(name));
        return ResponseEntity.ok(authorService.checkUnique(name));
    }
}
