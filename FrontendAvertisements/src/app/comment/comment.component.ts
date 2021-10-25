import {Component, OnInit} from "@angular/core";
import {CommentService} from "../services/comment.service";
import {Advertisement} from "../models/advertisement";
import {ActivatedRoute} from "@angular/router";
import {Comment} from "../models/comment";
import {AdvertisementService} from "../services/advertisement.service";

@Component({
  selector:'comment-component',
  templateUrl:'comment.component.html'
})
export class CommentComponent implements OnInit{

  advertisement!: Advertisement;
  comments: Comment[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private route: ActivatedRoute,
              private advertisementService: AdvertisementService,
              private commentService: CommentService) {
  }

  ngOnInit(): void {
    this.getAdvertisement();
    this.getComments();
    this.commentService.advertisement = this.advertisement;
  }

  getAdvertisement() {
    this.advertisementService.getAdvertisement(this.id)
      .subscribe(advertisement => this.advertisement = advertisement);
  }

  getComments(): void {
    this.commentService.getAllByAdvertisementId(this.id)
      .subscribe(comments => this.comments = comments);
  }
}
