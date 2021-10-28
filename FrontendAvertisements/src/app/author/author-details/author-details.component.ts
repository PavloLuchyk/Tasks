import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../../services/category/author.service";
import {LoginService} from "../../services/category/login.service";
import {ActivatedRoute} from "@angular/router";
import {AuthorView} from "../../models/author-view";

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
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAuthor();
  }

  getAuthor() {
    this.authorService.getAuthor(this.id)
      .subscribe(author => this.author = author);
  }

  isOwner(): boolean {
    if (this.loginService.userValue.id === this.id) {
      return true;
    }
    return false;
  }

}
