import { Component, OnInit } from "@angular/core";
import {Category} from "../../models/category";
import {CategoryService} from "../../services/category.service";
import {MessageService} from "../../services/message.service";
import {Advertisement} from "../../models/advertisement";
import {PageEvent} from "@angular/material/paginator";
import {DialogComponent} from "../../dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {LoginService} from "../../services/login.service";
import {StringCutter} from "../../Util/string-cutter";

@Component({
  selector: "category-name",
  templateUrl: "./category.component.html",
  styleUrls: ['category.component.css']
})

export class CategoryComponent implements OnInit{

  categories: Category[] = [];
  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;

  constructor(private categoryService: CategoryService,
              private loginService: LoginService,
              private dialog:MatDialog) {

  }

  getCategories(): void{
    this.categoryService.getCategoryInPages( 15, 0)
      .subscribe(categories => this.categories = categories);
    this.categoryService.getTotalAmountOfPages(15)
      .subscribe(
        length => this.length = length
      );
  }

  deleteCategory(id:number, name:string): void {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting category',
        message: 'Are you sure, you want to remove an advertisement: ' + name
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this.categories = this.categories.filter(c => c.id !== id);
        this.length--;
        this.categoryService.deleteCategory(id).subscribe();
      }
    });
  }

  isLogged():boolean {
    return this.loginService.isLogged;
  }

  isAdmin():boolean {
    return this.loginService.isAdmin;
  }

  getPages(event?: PageEvent) {
    this.categoryService.getTotalAmountOfPages(event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize=  event!.pageSize;
    this.pageIndex =  event!.pageIndex;
    this.categoryService.getCategoryInPages( event!.pageSize, event!.pageIndex)
      .subscribe(
        response => {
          this.categories = response;
        }
      );
    return event;
  }

  ngOnInit() {
    this.getCategories();
  }


  descriptionCutter(description:string): string {
    return StringCutter.descriptionCutter(description);
  }
}
