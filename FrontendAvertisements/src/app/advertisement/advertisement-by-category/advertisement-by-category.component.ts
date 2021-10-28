import {Component, Inject, OnInit} from "@angular/core";
import {CategoryService} from "../../services/category.service";
import {AdvertisementService} from "../../services/advertisement.service";
import {Category} from "../../models/category";
import {Advertisement} from "../../models/advertisement";
import {ActivatedRoute} from "@angular/router";
import {PageEvent} from "@angular/material/paginator";
import {BehaviorSubject, Observable, of} from "rxjs";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DialogComponent} from "../../dialog/dialog.component";
import {LoginService} from "../../services/login.service";

@Component({
  selector:'advertisement-by-component',
  templateUrl: 'advertisement-by-category.component.html',
  styleUrls: ['advertisement-by-category.component.css']
})
export class AdvertisementByCategoryComponent implements OnInit{

  category?: Category;
  advertisements: Advertisement[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));
  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;

  constructor(private route: ActivatedRoute,
              private categoryService:CategoryService,
              private advertisementService:AdvertisementService,
              private loginService:LoginService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getCategory();
    this.getAdvertisements();
  }

  getCategory(): void {
    this.categoryService.getCategory(this.id)
      .subscribe(category => {
        this.category = category;
        this.advertisementService.category = of(category);
      });
  }

  getPages(event?:PageEvent) {
    this.advertisementService.getTotalAmountOfPages(this.id, "category", event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize=  event!.pageSize;
    this.pageIndex =  event!.pageIndex;
    this.advertisementService.getAdvertisementInPages(this.id, "category", event!.pageSize, event!.pageIndex)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
    return event;
  }

  getAdvertisements(): void {
    this.advertisementService.getAdvertisementInPages(this.id, "category", 15, 0)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
    this.advertisementService.getTotalAmountOfPages(this.id, "category", 15)
      .subscribe(
        length => this.length = length
      );
  }

  deleteAdvertisement(id:number, title:string): void {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting advertisement',
        message: 'Are you sure, you want to remove an advertisement: ' + title
      }
    });
    confirmDialog.afterClosed().subscribe(result =>{
      if(result===true) {
        this.advertisements=this.advertisements.filter(advertisement => advertisement.id !== id);
        this.advertisementService.deleteAdvertisement(id)
          .subscribe();
      }
    })
  }

  isAdmin(): boolean {
    return this.loginService.isAdmin;
  }

  isLogged(): boolean {
    return this.loginService.isLogged;
  }
}
