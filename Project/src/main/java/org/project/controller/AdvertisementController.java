package org.project.controller;

import org.project.model.Advertisement;
import org.project.service.AdvertisementService;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class AdvertisementController {
    
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/advertisement/add")
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

    @GetMapping("/advertisement/pages/{number}")
    public ResponseEntity<Map<Integer, List<Advertisement>>> getAllInPages(@PathVariable int number) {
        Map<Integer, List<Advertisement>> pages = advertisementService.getAllInPages(PageSize.getFromSize(number));
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/advertisement")
    public ResponseEntity<List<Advertisement>> getAll(){
        List<Advertisement> advertisements = advertisementService.readAll();
        return ResponseEntity.ok(advertisements);
    }
    
    @GetMapping("/advertisement/{parent}/{id}")
    public ResponseEntity<List<Advertisement>> getAllByParent(@PathVariable String parent, @PathVariable long id) {
        List<Advertisement> advertisements = advertisementService.getAllByParentId(id, parent);
        return ResponseEntity.ok(advertisements);
    }

    @PutMapping("/advertisement/{id}")
    public ResponseEntity<Advertisement> update(@PathVariable("id") long id, @RequestBody Advertisement advertisement) {
        advertisement.setId(id);
        advertisement = advertisementService.update(advertisement);
        return ResponseEntity.ok(advertisement);
    }

    @DeleteMapping("/advertisement/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        advertisementService.delete(new Advertisement().setId(id));
        return ResponseEntity.ok("advertisement with id " + id + " has been deleted");
    }
}
