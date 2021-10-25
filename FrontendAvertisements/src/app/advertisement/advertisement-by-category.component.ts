import {Component, OnInit} from "@angular/core";
import {CategoryService} from "../services/category/category.service";
import {AdvertisementService} from "../services/advertisement.service";
import {Category} from "../models/category";
import {Advertisement} from "../models/advertisement";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector:'advertisement-by-component',
  templateUrl: 'advertisement-by-category.component.html'
})
export class AdvertisementByCategoryComponent implements OnInit{

  category?: Category;
  advertisements: Advertisement[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private route: ActivatedRoute,
              private categoryService:CategoryService,
              private advertisementService:AdvertisementService) {
  }

  ngOnInit(): void {
    this.getCategory();
    this.getAdvertisements()
    this.advertisementService.category = this.category;
  }

  getCategory(): void {
    this.categoryService.getCategory(this.id)
      .subscribe(category => {
        this.category = category;
      });
  }

  getAdvertisements(): void {
    this.advertisementService.getAllByCategoryId(this.id)
      .subscribe(advertisements => {
        this.advertisements = advertisements;
      });
  }
}
