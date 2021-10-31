import { Component, OnInit } from '@angular/core';
import {Advertisement} from "../../models/advertisement";
import {PageEvent} from "@angular/material/paginator";
import {ActivatedRoute} from "@angular/router";
import {CategoryService} from "../../services/category.service";
import {AdvertisementService} from "../../services/advertisement.service";
import {MatDialog} from "@angular/material/dialog";
import {of} from "rxjs";
import {DialogComponent} from "../../dialog/dialog.component";
import {AuthorView} from "../../models/author-view";
import {AuthorService} from "../../services/author.service";
import {LoginService} from "../../services/login.service";
import {StringCutter} from "../../Util/string-cutter";

@Component({
  selector: 'app-advertisement-by-author',
  templateUrl: './advertisement-by-author.component.html',
  styleUrls: ['./advertisement-by-author.component.css']
})
export class AdvertisementByAuthorComponent implements OnInit {

  authorView?: AuthorView;
  advertisements: Advertisement[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));
  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;

  constructor(private route: ActivatedRoute,
              private authorService: AuthorService,
              private advertisementService: AdvertisementService,
              private loginService: LoginService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getCategory();
    this.getAdvertisements();
    this.advertisementService.category = of(null!);
  }

  getCategory(): void {
    this.authorService.getAuthor(this.id)
      .subscribe(author => {
        this.authorView = author;
      });
  }

  getPages(event?: PageEvent) {
    this.advertisementService.getTotalAmountOfPages(this.id, "author", event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize = event!.pageSize;
    this.pageIndex = event!.pageIndex;
    this.advertisementService.getAdvertisementInPages(this.id, "author", event!.pageSize, event!.pageIndex)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
    return event;
  }

  getAdvertisements(): void {
    this.advertisementService.getAdvertisementInPages(this.id, "author", 15, 0)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
    this.advertisementService.getTotalAmountOfPages(this.id, "author", 15)
      .subscribe(
        length => this.length = length
      );
  }

  isOwner(): boolean {
    const user = this.loginService.userValue;
    if (user) {
      if (this.authorView?.email===user.username) {
        return true;
      }
    }
    return false;
  }

  isAdmin(): boolean {
    return this.loginService.isAdmin;
  }


  deleteAdvertisement(id: number, title: string): void {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting advertisement',
        message: 'Are you sure, you want to remove an advertisement: ' + title
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this.advertisements = this.advertisements.filter(advertisement => advertisement.id !== id);
        this.advertisementService.deleteAdvertisement(id)
          .subscribe();
      }
    })
  }

  descriptionCutter(description: string): string {
    return StringCutter.descriptionCutter(description);
  }

}
