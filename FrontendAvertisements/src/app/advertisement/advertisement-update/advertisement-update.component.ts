import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CategoryService} from "../../services/category.service";
import {AdvertisementService} from "../../services/advertisement.service";
import {ActivatedRoute, Route} from "@angular/router";
import {Location} from "@angular/common";
import {Advertisement} from "../../models/advertisement";
import {Category} from "../../models/category";

@Component({
  selector:'advertisement-update',
  templateUrl:'advertisement-update.component.html'
})
export class AdvertisementUpdateComponent implements OnInit{

  updateForm = new FormGroup({
    id: new FormControl('', Validators.required),
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    createDate: new FormControl('', Validators.required),
    author: new FormControl('', Validators.required),
    category: new FormControl('', Validators.required)
  });

  advertisement?: Advertisement;

  selectedCategory!: Category;

  categories: Category[] = [];

  constructor(private categoryService: CategoryService,
              private advertisementService: AdvertisementService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  onSubmit() {
    if(this.advertisement) {
      this.updateForm
        .patchValue(
          {category: this.categories.find(x => x.name===this.updateForm.get('category')?.value)}
        );
      this.advertisementService.updateAdvertisement(this.updateForm.value)
        .subscribe(() => this.goBack());
    }
  }

  goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    this.getAdvertisement();
    this.getCategories();
  }


  getAdvertisement() : void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.advertisementService.getAdvertisement(id)
      .subscribe(advertisement => {
        this.advertisement = advertisement;
        this.updateForm.setValue({
          id:advertisement.id,
          title: advertisement.title,
          description: advertisement.description,
          createDate: advertisement.createDate,
          author: advertisement.author,
          category: advertisement.category
        });
      })
  }

  getCategories() {
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
  }

}
