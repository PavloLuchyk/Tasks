import {Component, OnInit} from "@angular/core";
import {AdvertisementService} from "../services/advertisement.service";
import {Advertisement} from "../models/advertisement";

@Component({
  selector:'advertisements',
  templateUrl:'advertisement.component.html'
})
export class AdvertisementComponent implements OnInit{

  advertisements: Advertisement[] = [];

  constructor(private advertisementService: AdvertisementService) {
  }

  getAdvertisements(): void {
    this.advertisementService.getAdvertisements()
      .subscribe(advertisements => this.advertisements = advertisements);
  }

  ngOnInit(): void {
    this.getAdvertisements();
  }

  deleteAdvertisement(id: number): void {

  }

}
