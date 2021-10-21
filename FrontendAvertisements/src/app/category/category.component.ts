import { Component, OnInit } from "@angular/core";
import {Category} from "./category";
import {CategoryService} from "../services/category/category.service";
import {MessageService} from "../services/message.service";

@Component({
  selector: "category-name",
  templateUrl: "./category.component.html",
})

export class CategoryComponent implements OnInit{

  categories: Category[] = [];

  constructor(private categoryService: CategoryService,private messageService: MessageService) {

  }

  getCategories(): void{
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
  }

  deleteCategory(id: number): void {
    this.categories = this.categories.filter(c => c.id !== id);
    this.categoryService.deleteCategory(id).subscribe();
  }

  ngOnInit() {
    this.getCategories();
  }

}
