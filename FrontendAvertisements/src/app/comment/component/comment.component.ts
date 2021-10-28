import {Component, OnDestroy, OnInit} from "@angular/core";
import {CommentService} from "../../services/comment.service";
import {Advertisement} from "../../models/advertisement";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {Comment} from "../../models/comment";
import {AdvertisementService} from "../../services/advertisement.service";
import {PageEvent} from "@angular/material/paginator";
import {of} from "rxjs";
import {LoginService} from "../../services/login.service";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../dialog/dialog.component";
import {CommentUpdateComponent} from "../comment-update/comment-update.component";

@Component({
  selector:'comment-component',
  templateUrl:'comment.component.html',
  styleUrls: ['comment.component.css']
})
export class CommentComponent implements OnInit, OnDestroy{

  advertisement!: Advertisement;
  comments: Comment[] = [];
  id = Number(this.route.snapshot.paramMap.get('id'));
  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;

  mySubscription: any;

  constructor(private route: ActivatedRoute,
              private advertisementService: AdvertisementService,
              private commentService: CommentService,
              private loginService: LoginService,
              private dialog: MatDialog,
              private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
    this.mySubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.router.navigated = false;
      }
    });
  }

  ngOnInit(): void {
    this.getAdvertisement();
    this.getComments();
    this.commentService.advertisement = of(this.advertisement);
  }

  getAdvertisement() {
    this.advertisementService.getAdvertisement(this.id)
      .subscribe(advertisement => this.advertisement = advertisement);
  }

  getComments(): void {
    this.commentService.getTotalAmountOfPages(this.id, "advertisement", 15)
      .subscribe(
        length => {
          this.length = length;
        }
      );
    this.commentService.getCommentsInPages(this.id, "advertisement", 15, 0)
      .subscribe(
        response => {
          this.comments = response;
        }
      );
  }

  addComment() {
    const url = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([url]);
    });
  }

  getPages(event?:PageEvent) {
    this.commentService.getTotalAmountOfPages(this.id, "advertisement", event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize=  event!.pageSize;
    this.pageIndex =  event!.pageIndex;
    this.commentService.getCommentsInPages(this.id, "advertisement", event!.pageSize, event!.pageIndex)
      .subscribe(
        response => {
          this.comments = response;
        }
      );
    return event;
  }

  isLogged(): boolean {
    return this.loginService.isLogged;
  }

  isAdmin(): boolean {
    return this.loginService.isAdmin;
  }

  isOwner(id: number): boolean {
    const user = this.loginService.userValue;
    if (user) {
      if (user.id===id||user.id===this.advertisement?.author.id) {
        return true;
      }
    }
    return false;
  }

  updateComment(comment: Comment) {
    const confirmDialog = this.dialog.open(CommentUpdateComponent, {
      data: comment
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result) {
        this.commentService.updateComment(result)
          .subscribe();
        const url = this.router.url;
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate([url]);
        });
      }
    })
  }

  deleteComment(id: number) {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting comment',
        message: 'Are you sure, you want to remove this comment'
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this.comments = this.comments.filter(comment => comment.id !== id);
        this.length--;
        this.commentService.deleteComment(id)
          .subscribe();
      }
    })
  }

  ngOnDestroy(): void {
    this.mySubscription.unsubscribe();
  }
}
