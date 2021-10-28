import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../../services/author.service";
import {PageEvent} from "@angular/material/paginator";
import {AuthorView} from "../../models/author-view";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../dialog/dialog.component";

@Component({
  selector: 'app-author-all',
  templateUrl: './author-all.component.html',
  styleUrls: ['./author-all.component.css']
})
export class AuthorAllComponent implements OnInit {

  authors: AuthorView[] = [];
  length = 500;
  pageSize = 15;
  pageIndex = 0;
  pageEvent?: PageEvent;

  constructor(private authorService: AuthorService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAuthors();
  }

  getAuthors(): void {
    this.authorService.getTotalNumberOfPages(15)
      .subscribe(length=> this.length = length);
    this.authorService.getAuthorsInPages(15,0 )
      .subscribe(authors => this.authors = authors);
  }

  getPages(event?:PageEvent) {
    this.authorService.getTotalNumberOfPages(event!.pageSize)
      .subscribe(
        length => this.length = length
      );
    this.pageSize=  event!.pageSize;
    this.pageIndex =  event!.pageIndex;
    this.authorService.getAuthorsInPages(event!.pageSize,event!.pageIndex )
      .subscribe(authors => this.authors = authors);
    return event;
  }

  deleteAuthor(id:number, email:string) {
    const confirmDialog = this.dialog.open(DialogComponent, {
      data: {
        title: 'Confirm deleting author',
        message: 'Are you sure, you want to remove author with email: ' + email
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
        this.authors = this.authors.filter(advertisement => advertisement.id !== id);
        this.authorService.deleteAuthor(id)
          .subscribe();
      }
    })
  }
}
