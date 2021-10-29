package org.project.controller;

import org.project.model.Advertisement;
import org.project.model.Comment;
import org.project.service.AdvertisementService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class AdvertisementController {
    
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/advertisement/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Advertisement> insert(@RequestBody Advertisement advertisement) {
        advertisement = advertisementService.create(advertisement);
        return ResponseEntity.ok(advertisement);
    }

    @GetMapping("/advertisement/{id}")
    public ResponseEntity<Advertisement> getById(@PathVariable long id) {
        Advertisement advertisement = advertisementService.readById(id);
        return ResponseEntity.ok().body(advertisement);
    }

    @GetMapping("/advertisement/sorted/{order}")
    public ResponseEntity<List<Advertisement>> getAllSorted(@PathVariable String order) {
        List<Advertisement> advertisements = advertisementService.readAllSorted(SortingOrder.valueOf(order.toUpperCase()));
        return ResponseEntity.ok(advertisements);
    }

    @GetMapping("/advertisement/page/{number}/{pageNumber}")
    public ResponseEntity<List<Advertisement>> getAllInPages(@PathVariable int number,@PathVariable int pageNumber) {
        List<Advertisement> page = advertisementService.getAllInPages(PageSize.getFromSize(number), pageNumber);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/advertisement/page/{number}")
    public ResponseEntity<Long> getNumberOfAllPages(@PathVariable int number) {
        return ResponseEntity.ok(advertisementService.getCountOfAllPages(PageSize.getFromSize(number)));
    }

    @GetMapping("/advertisement")
    public ResponseEntity<List<Advertisement>> getAll(){
        List<Advertisement> advertisements = advertisementService.readAll();
        return ResponseEntity.ok(advertisements);
    }
    
    @GetMapping("/advertisement/parent/{parent}/{id}")
    public ResponseEntity<List<Advertisement>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<Advertisement> advertisements = advertisementService.getAllByParentId(id, parent);
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
    public ResponseEntity<Long> getTotalCountOfPages(@PathVariable String parent,
                                                     @PathVariable long id,
                                                     @PathVariable int pageSize) {
        Long advertisements = advertisementService.getTotalCountOfPages(id,parent,PageSize.getFromSize(pageSize));
        return ResponseEntity.ok(advertisements);
    }

    @PutMapping("/advertisement/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @advertisementController.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Advertisement> update(@PathVariable("id") long id, @RequestBody Advertisement advertisement) {
        advertisement.setId(id);
        advertisement = advertisementService.update(advertisement);
        return ResponseEntity.ok(advertisement);
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
