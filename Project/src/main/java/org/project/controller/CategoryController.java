package org.project.controller;

import org.project.dto.CategoryDto;
import org.project.dto.mapper.DtoMapper;
import org.project.model.Category;
import org.project.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;
    private final DtoMapper<Category, CategoryDto> dtoMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, DtoMapper<Category, CategoryDto> dtoMapper) {
        this.categoryService = categoryService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/category/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> insert(@RequestBody Category category) {
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

    @GetMapping("/category/pages/{number}/{pageNumber}")
    public ResponseEntity<List<Category>> getAllInPages(@PathVariable int number, @PathVariable int pageNumber) {
        List<Category> pages = categoryService.getAllInPages(PageSize.getFromSize(number),pageNumber);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/category/pages/{number}")
    public ResponseEntity<Long> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(categoryService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.readAll();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> update(@PathVariable("id") long id, @RequestBody Category category) {
        category.setId(id);
        category = categoryService.update(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        categoryService.delete(categoryService.readById(id));
        return ResponseEntity.ok("Category with id " + id + " has been deleted");
    }

    @GetMapping("/category/check")
    public ResponseEntity<?> checkUnique(@RequestParam("name") String name) {
        return ResponseEntity.ok(categoryService.checkUnique(name));
    }

}
