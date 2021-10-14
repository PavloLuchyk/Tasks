package org.project.controller;

import org.project.model.Category;
import org.project.service.CategoryService;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/category/add")
    public ResponseEntity<Category> insert(@RequestBody Category category) {
        System.out.println("Json " + category);
        category = categoryService.create(category);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getById(@PathVariable long id) {
        Category category = categoryService.readById(id);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/category/sorted/{order}")
    public ResponseEntity<List<Category>> getAllSorted(@PathVariable String order) {
        List<Category> category = categoryService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()));
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/pages/{number}")
    public ResponseEntity<Map<Integer, List<Category>>> getAllInPages(@PathVariable int number) {
        Map<Integer, List<Category>> pages = categoryService.getAllInPages(PageSize.getFromSize(number));
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.readAll();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") long id, @RequestBody Category category) {
        category.setId(id);
        category = categoryService.update(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        categoryService.delete(new Category().setId(id));
        return ResponseEntity.ok("Category with id " + id + " has been deleted");
    }
}
