package org.project.controller;

import org.project.dto.AdvertisementDto;
import org.project.dto.mapper.impl.AdvertisementDtoMapper;
import org.project.model.Advertisement;
import org.project.service.AdvertisementService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.project.service.AuthorService;
import org.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.project.controller.EndPoints.*;

@CrossOrigin(origins="*")
@RequestMapping(ADVERTISEMENT)
@RestController
public class AdvertisementController {
    
    private final AdvertisementService advertisementService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final AdvertisementDtoMapper advertisementDtoMapper;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService, CategoryService categoryService, AuthorService authorService, AdvertisementDtoMapper advertisementDtoMapper) {
        this.advertisementService = advertisementService;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.advertisementDtoMapper = advertisementDtoMapper;
    }

    @PostMapping(ADD)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<AdvertisementDto> insert(@RequestBody AdvertisementDto advertisementDto) {
        Advertisement advertisement = advertisementDtoMapper.mapToEntity(
                advertisementDto,
                categoryService.readById(advertisementDto.getCategoryId()),
                authorService.readById(advertisementDto.getAuthorId())
        );
        advertisement = advertisementService.create(advertisement);
        advertisementDto = advertisementDtoMapper.mapToDto(advertisement);
        return ResponseEntity.ok(advertisementDto);
    }

    @GetMapping(ID)
    public ResponseEntity<AdvertisementDto> getById(@PathVariable long id) {
        AdvertisementDto advertisement = advertisementDtoMapper.mapToDto(advertisementService.readById(id));
        return ResponseEntity.ok(advertisement);
    }

    @GetMapping(SORTED)
    public ResponseEntity<List<AdvertisementDto>> getAllSorted(@PathVariable String order) {
        List<AdvertisementDto> advertisements = advertisementService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping(PAGES_GET_PAGE)
    public ResponseEntity<List<AdvertisementDto>> getAllInPages(@PathVariable int number,@PathVariable int pageNumber) {
        List<AdvertisementDto> page = advertisementService.getAllInPages(PageSize.getFromSize(number), pageNumber)
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(page);
    }

    @GetMapping(PAGES_COUNT)
    public ResponseEntity<Number> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(advertisementService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAll(){
        List<AdvertisementDto> advertisements = advertisementService.readAll()
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }
    
    @GetMapping(PARENT_BY_ID)
    public ResponseEntity<List<AdvertisementDto>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<AdvertisementDto> advertisements = advertisementService.getAllByParentId(id, parent)
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping(PARENT_BY_ID_PAGE)
    public ResponseEntity<List<AdvertisementDto>> getAllByParentInPages(@PathVariable String parent,
                                                               @PathVariable long id,
                                                               @PathVariable int pageSize,
                                                               @PathVariable int pageNumber) {
        List<AdvertisementDto> comments = advertisementService.getAllByParentIdInPages(
                id,
                parent,
                PageSize.getFromSize(pageSize),
                pageNumber)
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping(PARENT_BY_ID_COUNT)
    public ResponseEntity<Number> getTotalCountOfPages(@PathVariable String parent,
                                                     @PathVariable long id,
                                                     @PathVariable int pageSize) {
        Number advertisements = advertisementService.getTotalCountOfPages(id,parent,PageSize.getFromSize(pageSize));
        return ResponseEntity.ok(advertisements);
    }

    @PutMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or @advertisementController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<AdvertisementDto> update(@PathVariable("id") long id, @RequestBody AdvertisementDto advertisementDto) {
        advertisementDto.setId(id);
        Advertisement advertisement = advertisementDtoMapper.mapToEntity(
                advertisementDto,
                categoryService.readById(advertisementDto.getCategoryId()),
                authorService.readById(advertisementDto.getAuthorId())
                );
        advertisement = advertisementService.update(advertisement);
        advertisementDto = advertisementDtoMapper.mapToDto(advertisement);
        return ResponseEntity.ok(advertisementDto);
    }

    @DeleteMapping(ID)
    @PreAuthorize("hasAuthority('ADMIN') or @advertisementController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        advertisementService.delete(advertisementService.readById(id));
        return ResponseEntity.ok("advertisement with id " + id + " has been deleted");
    }

    public boolean isOwner(long advertisementId, long id) {
        return id == advertisementService.readById(advertisementId).getAuthor().getId();
    }
}
