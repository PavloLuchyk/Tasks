import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../../services/category/author.service";
import {LoginService} from "../../services/category/login.service";
import {AuthorView} from "../../models/author-view";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  author?: AuthorView;

  constructor(private authorService: AuthorService,
              private loginService: LoginService) { }

  ngOnInit(): void {
    this.getAuthor();
  }

  getAuthor(): void {
    this.authorService.getAuthorByEmail(this.loginService.userValue.username)
      .subscribe(author => this.author = author);
  }
}
