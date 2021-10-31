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

import static org.project.controller.EndPoints.*;

@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping(AUTHOR)
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

    @PostMapping(ADD)
    public ResponseEntity<AuthorDto> insert(@RequestBody AuthorDto authorDto) {
        Author author = dtoMapper.mapToEntity(authorDto);
        author = authorService.create(author);
        authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping(ID)
    public ResponseEntity<AuthorDto> getById(@PathVariable long id) {
        Author author = authorService.readById(id);
        AuthorDto authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping(AUTHOR_GET_BY_EMAIL)
    public ResponseEntity<AuthorDto> getById(@PathVariable String email) {
        Author author = authorService.getByEmail(email);
        AuthorDto authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @GetMapping(SORTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAllSorted(@PathVariable String order) {
        List<AuthorDto> authors =
                authorService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                        .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @GetMapping(PAGES_GET_PAGE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAllInPages(@PathVariable int number, @PathVariable int pageNumber) {
        List<AuthorDto> page =
                authorService.getAllInPages(PageSize.getFromSize(number), pageNumber)
                        .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(page);
    }

    @GetMapping(PAGES_COUNT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Number> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(authorService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthorDto>> getAll(){
        List<AuthorDto> authors = authorService.readAll()
                .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @PutMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<AuthorDto> update(@PathVariable("id") long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        Author author = dtoMapper.mapToEntity(authorDto);
        author = authorService.update(author);
        authorDto = dtoMapper.mapToDto(author);
        return ResponseEntity.ok(authorDto);
    }

    @DeleteMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        authorService.delete(authorService.readById(id));
        return ResponseEntity.ok("Author with id " + id + " has been deleted");
    }

    @GetMapping(CHECK)
    public ResponseEntity<?> checkUnique(@RequestParam("email") String name) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("unique", authorService.checkUnique(name));
        return ResponseEntity.ok(authorService.checkUnique(name));
    }

    @PostMapping(CHECK)
    public ResponseEntity<Boolean> isIdentical(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.isIdentical(author.getPassword(),author.getId()));
    }
}
