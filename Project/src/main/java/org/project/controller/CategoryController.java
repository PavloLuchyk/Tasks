package org.project.controller;

import org.project.dto.CategoryDto;
import org.project.dto.mapper.SimpleDtoEntityMapper;
import org.project.model.Category;
import org.project.service.CategoryService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final SimpleDtoEntityMapper<Category, CategoryDto> dtoMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, SimpleDtoEntityMapper<Category, CategoryDto> dtoMapper) {
        this.categoryService = categoryService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/category/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDto> insert(@RequestBody CategoryDto categoryDto) {
        Category category = dtoMapper.mapToEntity(categoryDto);
        category = categoryService.create(category);
        categoryDto = dtoMapper.mapToDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable long id) {
        Category category = categoryService.readById(id);
        CategoryDto categoryDto = dtoMapper.mapToDto(category);
        return ResponseEntity.ok().body(categoryDto);
    }

    @GetMapping("/category/sorted/{order}")
    public ResponseEntity<List<CategoryDto>> getAllSorted(@PathVariable String order) {
        List<CategoryDto> category =
                categoryService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                .stream().map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/pages/{number}/{pageNumber}")
    public ResponseEntity<List<CategoryDto>> getAllInPages(@PathVariable int number, @PathVariable int pageNumber) {
        List<CategoryDto> pages =
                categoryService.getAllInPages(PageSize.getFromSize(number),pageNumber)
                .stream().map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/category/pages/{number}")
    public ResponseEntity<Number> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(categoryService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getAll(){
        List<CategoryDto> categories =
                categoryService.readAll()
                .stream().map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") long id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        Category category = dtoMapper.mapToEntity(categoryDto);
        category = categoryService.update(category);
        categoryDto = dtoMapper.mapToDto(category);
        return ResponseEntity.ok(categoryDto);
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
