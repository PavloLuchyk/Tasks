import {Component, OnInit} from "@angular/core";
import {AdvertisementService} from "../../services/advertisement.service";
import {Advertisement} from "../../models/advertisement";
import {PageEvent} from "@angular/material/paginator";
import {DialogComponent} from "../../dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {LoginService} from "../../services/login.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector:'advertisements',
  templateUrl:'advertisement.component.html',
  styleUrls:['advertisement.component.css']
})
export class AdvertisementComponent implements OnInit{

  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;
  advertisements: Advertisement[] = [];
  title?: string;

  constructor(private advertisementService: AdvertisementService,
              private loginService: LoginService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog) {
  }

  getAdvertisements(): void {
    this.advertisementService.getNumberOfAllAdvertisements( 15)
      .subscribe(
        length => this.length = length
      );
    this.advertisementService.getAllAdvertisementInPages(15, 0)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
  }

  ngOnInit(): void {
    this.getAdvertisements();
  }

  getPages(event?:PageEvent) {
    this.advertisementService.getNumberOfAllAdvertisements(event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize=  event!.pageSize;
    this.pageIndex =  event!.pageIndex;
    this.advertisementService.getAllAdvertisementInPages(event!.pageSize, event!.pageIndex)
      .subscribe(
        response => {
          this.advertisements = response;
        }
      );
    return event;
  }

  descriptionCutter(description: string): string {
    if (description.length > 57) {
      return description.substring(0, 57) + "...";
    } else {
      return description
    }
  }

  deleteAdvertisement(id:number, title:string): void {
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



  isLogged() {
    return this.loginService.isLogged;
  }

  isAdmin() {
    return this.loginService.isAdmin;
  }
}
