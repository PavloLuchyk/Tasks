import {Component, OnDestroy, OnInit} from "@angular/core";
import {CommentService} from "../services/comment.service";
import {Advertisement} from "../models/advertisement";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Comment} from "../models/comment";
import {AdvertisementService} from "../services/advertisement.service";

@Component({
  selector:'comment-component',
  templateUrl:'comment.component.html'
})
export class CommentComponent implements OnInit, OnDestroy{

  advertisement!: Advertisement;
  comments: Comment[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));

  mySubscription: any;

  constructor(private route: ActivatedRoute,
              private advertisementService: AdvertisementService,
              private commentService: CommentService,
              private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
    this.mySubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Trick the Router into believing it's last link wasn't previously loaded
        this.router.navigated = false;
      }
    });
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

  addComment() {
    const url = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([url]);
    });
  }

  ngOnDestroy(): void {
    this.mySubscription.unsubscribe();
  }
}
