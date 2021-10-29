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

@CrossOrigin(origins="*")
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

    @PostMapping("/advertisement/add")
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

    @GetMapping("/advertisement/{id}")
    public ResponseEntity<AdvertisementDto> getById(@PathVariable long id) {
        AdvertisementDto advertisement = advertisementDtoMapper.mapToDto(advertisementService.readById(id));
        return ResponseEntity.ok(advertisement);
    }

    @GetMapping("/advertisement/sorted/{order}")
    public ResponseEntity<List<AdvertisementDto>> getAllSorted(@PathVariable String order) {
        List<AdvertisementDto> advertisements = advertisementService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()))
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/advertisement/page/{number}/{pageNumber}")
    public ResponseEntity<List<AdvertisementDto>> getAllInPages(@PathVariable int number,@PathVariable int pageNumber) {
        List<AdvertisementDto> page = advertisementService.getAllInPages(PageSize.getFromSize(number), pageNumber)
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/advertisement/page/{number}")
    public ResponseEntity<Number> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(advertisementService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping("/advertisement")
    public ResponseEntity<List<AdvertisementDto>> getAll(){
        List<AdvertisementDto> advertisements = advertisementService.readAll()
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }
    
    @GetMapping("/advertisement/parent/{parent}/{id}")
    public ResponseEntity<List<AdvertisementDto>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<AdvertisementDto> advertisements = advertisementService.getAllByParentId(id, parent)
                .stream().map(advertisementDtoMapper::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/advertisement/parent/{parent}/{id}/{pageSize}/{pageNumber}")
    public ResponseEntity<List<Advertisement>> getAllByParentInPages(@PathVariable String parent,
                                                               @PathVariable long id,
                                                               @PathVariable int pageSize,
                                                               @PathVariable int pageNumber) {
        List<Advertisement> comments = advertisementService.getAllByParentIdInPages(
                id,
                parent,
                PageSize.getFromSize(pageSize),
                pageNumber);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/advertisement/parent/{parent}/{id}/{pageSize}")
    public ResponseEntity<Number> getTotalCountOfPages(@PathVariable String parent,
                                                     @PathVariable long id,
                                                     @PathVariable int pageSize) {
        Number advertisements = advertisementService.getTotalCountOfPages(id,parent,PageSize.getFromSize(pageSize));
        return ResponseEntity.ok(advertisements);
    }

    @PutMapping("/advertisement/{id}")
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

    @DeleteMapping("/advertisement/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @advertisementController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        advertisementService.delete(advertisementService.readById(id));
        return ResponseEntity.ok("advertisement with id " + id + " has been deleted");
    }

    public boolean isOwner(long advertisementId, long id) {
        return id == advertisementService.readById(advertisementId).getAuthor().getId();
    }
}
