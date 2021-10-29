package org.project.controller;

import org.project.dto.AuthorDto;
import org.project.dto.mapper.SimpleDtoEntityMapper;
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
import java.util.stream.Collectors;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final SimpleDtoEntityMapper<Author, AuthorDto> dtoMapper;

    @Autowired
    public AuthorController(AuthorService authorService,
                            SimpleDtoEntityMapper<Author, AuthorDto> dtoMapper) {
        this.authorService = authorService;
        this.dtoMapper =dtoMapper;
    }

    @PostMapping("/author/add")
    public ResponseEntity<AuthorDto> insert(@RequestBody AuthorDto authorDto) {
        Author author = dtoMapper.mapToEntity(authorDto);
        author = authorService.create(author);
        authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable long id) {
        Author author = authorService.readById(id);
        AuthorDto authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/author/email/{email}")
    public ResponseEntity<AuthorDto> getById(@PathVariable String email) {
        Author author = authorService.getByEmail(email);
        AuthorDto authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping("/author/sorted/{order}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAllSorted(@PathVariable String order) {
        List<AuthorDto> authors =
                authorService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                        .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/author/pages/{number}/{pageNumber}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAllInPages(@PathVariable int number, @PathVariable int pageNumber) {
        List<AuthorDto> page =
                authorService.getAllInPages(PageSize.getFromSize(number), pageNumber)
                        .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/author/pages/{number}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(authorService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping("/author")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAll(){
        List<AuthorDto> authors = authorService.readAll()
                .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/author/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<AuthorDto> update(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        Author author = dtoMapper.mapToEntity(authorDto);
        author = authorService.update(author);
        authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
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

    @PostMapping("/author/check/password")
    public ResponseEntity<Boolean> isIdentical(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.isIdentical(author.getPassword(),author.getId()));
    }
}
