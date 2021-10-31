package org.project.controller;

import org.project.dto.CommentDto;
import org.project.dto.mapper.impl.CommentDtoMapper;
import org.project.model.Comment;
import org.project.service.AdvertisementService;
import org.project.service.AuthorService;
import org.project.service.CommentService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.project.controller.EndPoints.*;

@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping(COMMENT)
@RestController
public class CommentController {
    
    private final CommentService commentService;
    private final AuthorService authorService;
    private final AdvertisementService advertisementService;
    private final CommentDtoMapper commentDtoMapper;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentDtoMapper commentDtoMapper,
                             AdvertisementService advertisementService,
                             AuthorService authorService) {
        this.commentService = commentService;
        this.commentDtoMapper = commentDtoMapper;
        this.advertisementService = advertisementService;
        this.authorService = authorService;
    }

    @PostMapping(ADD)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<CommentDto> insert(@RequestBody CommentDto commentDto) {
        Comment comment = commentDtoMapper.mapToEntity(
                commentDto,
                advertisementService.readById(commentDto.getAdvertisementId()),
                authorService.readById(commentDto.getAuthorId())
                );
        comment = commentService.create(comment);
        commentDto = commentDtoMapper.mapToDto(comment);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping(ID)
    public ResponseEntity<CommentDto> getById(@PathVariable long id) {
        CommentDto comment = commentDtoMapper.mapToDto(commentService.readById(id));
        return ResponseEntity.ok(comment);
    }

    @GetMapping(SORTED)
    public ResponseEntity<List<CommentDto>> getAllSorted(@PathVariable String order) {
        List<CommentDto> comments = commentService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                .stream().map(commentDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping(PAGES_GET_PAGE)
    public ResponseEntity<List<CommentDto>> getAllInPages(@PathVariable int number, @PathVariable int pageNumber) {
        List<CommentDto> pages = commentService.getAllInPages(PageSize.getFromSize(number), pageNumber)
                .stream().map(commentDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(pages);
    }

    @GetMapping(PAGES_COUNT)
    public ResponseEntity<Number> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(commentService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAll(){
        List<CommentDto> comments = commentService.readAll()
                .stream().map(commentDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping(PARENT_BY_ID)
    public ResponseEntity<List<CommentDto>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<CommentDto> comments = commentService.getAllByParentId(id, parent)
                .stream().map(commentDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping(PARENT_BY_ID_PAGE)
    public ResponseEntity<List<CommentDto>> getAllByParentInPages(@PathVariable String parent,
                                                               @PathVariable long id,
                                                               @PathVariable int pageSize,
                                                               @PathVariable int pageNumber) {
        List<CommentDto> comments
                = commentService.getAllByParentIdInPages(id, parent,PageSize.getFromSize(pageSize), pageNumber)
                .stream().map(commentDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }
    @GetMapping(PARENT_BY_ID_COUNT)
    public ResponseEntity<Number> getTotalCountOfPages(@PathVariable String parent,
                                                              @PathVariable long id,
                                                              @PathVariable int pageSize) {
        Number comments = commentService.getTotalCountOfPages(id,parent,PageSize.getFromSize(pageSize));
        return ResponseEntity.ok(comments);
    }

    @PutMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or @commentController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<CommentDto> update(@PathVariable("id") long id, @RequestBody CommentDto commentDto) {
        commentDto.setId(id);
        Comment comment = commentDtoMapper.mapToEntity(
                commentDto,
                advertisementService.readById(commentDto.getAdvertisementId()),
                authorService.readById(commentDto.getAuthorId())
        );
        comment = commentService.update(comment);
        commentDto = commentDtoMapper.mapToDto(comment);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or @commentController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        commentService.delete(commentService.readById(id));
        return ResponseEntity.ok("comment with id " + id + " has been deleted");
    }

    public boolean isOwner(long commentId, long id) {
        Comment current = commentService.readById(commentId);
        return id == current.getAuthor().getId()
                || id == current.getAdvertisement().getAuthor().getId();
    }
}
