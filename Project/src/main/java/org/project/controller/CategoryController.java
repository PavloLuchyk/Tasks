package org.project.controller;

import org.project.model.Category;
import org.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/add")
    public ResponseEntity<?> insert(@RequestBody Category category) {
        System.out.println("Json " + category);
        category = categoryService.create(category);
        return ResponseEntity.ok().body("New Book has been saved with ID:" + category.getId());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getById(@PathVariable long id) {
        Category category = categoryService.readById(id);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.readAll();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Category category) {
        category.setId(id);
        categoryService.update(category);
        return ResponseEntity.ok("Category updated successfully " + category.getId());
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        categoryService.delete(new Category().setId(id));
        return ResponseEntity.ok("Category with id " + id + " has been deleted");
    }
}
