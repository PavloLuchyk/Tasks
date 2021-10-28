import {Component, OnInit, Input} from "@angular/core";
import {Category} from "../../models/category";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import {CategoryService} from "../../services/category.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector:"category-details",
  templateUrl:"./category-update.component.html",
  styleUrls: ["./category-update.component.css"]
})
export class CategoryUpdateComponent implements OnInit{

   category?:Category;

   updateForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private location: Location
  ) {
    this.updateForm = new FormGroup({
      id: new FormControl(),
      name: new FormControl(),
      description: new FormControl(),
      createDate: new FormControl()
    })
  }

    ngOnInit() {
      this.getCategory();
    }

    getCategory(): void {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.categoryService.getCategory(id)
        .subscribe(category => {
          this.category = category;
          this.updateForm.setValue({
            id: this.category!.id,
            name: this.category!.name,
            description: this.category!.description,
            createDate: this.category!.createDate
          });
        });

    }

    goBack(): void {
      this.location.back();
    }

    save(): void {
      if(this.category) {
        this.categoryService.updateCategory(this.updateForm.value)
          .subscribe(() => this.goBack());
      }

    }
}
