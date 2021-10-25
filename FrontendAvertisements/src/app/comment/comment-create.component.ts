import {Component, Input, OnInit} from "@angular/core";
import {CommentService} from "../services/comment.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../services/category/login.service";
import {AuthorService} from "../services/category/author.service";
import {AuthorView} from "../models/author-view";
import {Advertisement} from "../models/advertisement";

@Component({
  selector: 'comment-create',
  templateUrl:'comment-create.component.html'
})
export class CommentCreateComponent implements OnInit{

  author!: AuthorView;

  @Input()advertisement?: Advertisement;

  commentForm = new FormGroup({
    text: new FormControl('', Validators.required),
    author: new FormControl(''),
    advertisement: new FormControl('')
  })

  constructor(private commentService: CommentService,
              private authorService: AuthorService,
              private loginService: LoginService) {
  }

  onSubmit(): void {
    this.commentForm.patchValue({
      advertisement: this.advertisement,
      author: this.author
    });
    this.commentService.saveComment(this.commentForm.value).subscribe();
  }

  ngOnInit(): void {
    this.getAuthor()
  }

  getAuthor(): void {
    this.authorService.getAuthorByEmail(this.loginService.userValue.username)
      .subscribe(author => this.author = author);
  }

}