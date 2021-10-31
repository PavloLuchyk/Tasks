import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../../services/author.service";
import {LoginService} from "../../services/login.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorView} from "../../models/author-view";
import {DialogComponent} from "../../dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-author-details',
  templateUrl: './author-details.component.html',
  styleUrls: ['./author-details.component.css']
})
export class AuthorDetailsComponent implements OnInit {

  author?:AuthorView;
  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private authorService: AuthorService,
              private loginService:LoginService,
              private router: Router,
              private route: ActivatedRoute,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAuthor();
  }

  getAuthor() {
    this.authorService.getAuthor(this.id)
      .subscribe(author => this.author = author);
  }

  isOwner(): boolean {
    const user = this.loginService.userValue;
    if (user) {
      if (user.id === this.id) {
        return true;
      }
    }
    return false;
  }

  isAdmin() {
    return this.loginService.isAdmin
  }


  deleteAuthor() {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting your account',
        message: 'Are you sure, you want delete YOUR ACCOUNT? '
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this.authorService.deleteAuthor(this.author?.id!)
          .subscribe();
        this.loginService.logout();
        this.router.navigateByUrl('/categories')
          .then();
      }
    })
  }
}
