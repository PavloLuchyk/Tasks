import { Component, OnInit } from "@angular/core";
import {Category} from "../models/category";
import {CategoryService} from "../services/category/category.service";
import {MessageService} from "../services/message.service";
import {AuthorizationUtils} from "../security/authorization-utils";

@Component({
  selector: "category-name",
  templateUrl: "./category.component.html",
})

export class CategoryComponent implements OnInit{

  categories: Category[] = [];

  constructor(private categoryService: CategoryService,private authorizationUtils:AuthorizationUtils) {

  }

  getCategories(): void{
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
  }

  deleteCategory(id: number): void {
    this.categories = this.categories.filter(c => c.id !== id);
    this.categoryService.deleteCategory(id).subscribe();
  }

  isAdmin() {
    return this.authorizationUtils.isAdmin();
  }

  ngOnInit() {
    this.getCategories();
  }

}
