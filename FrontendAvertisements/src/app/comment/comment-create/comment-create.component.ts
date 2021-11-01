import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {CommentService} from "../../services/comment.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {AuthorService} from "../../services/author.service";
import {AuthorView} from "../../models/author-view";
import {Advertisement} from "../../models/advertisement";
import {Comment} from "../../models/comment";

@Component({
  selector: 'comment-create',
  templateUrl:'comment-create.component.html',
  styleUrls: ['comment-create.component.css']
})
export class CommentCreateComponent implements OnInit{

  author!: AuthorView;

  @Input()advertisement?: Advertisement;

  @Output() event: EventEmitter<Comment> = new EventEmitter<Comment>();

  maxLength = 300;

  commentForm = new FormGroup({
    text: new FormControl('', Validators.required),
    authorId: new FormControl(''),
    advertisementId: new FormControl('')
  })

  constructor(private commentService: CommentService,
              private authorService: AuthorService,
              private loginService: LoginService) {
  }

  onSubmit(): void {
    this.commentForm.patchValue({
      advertisementId: this.advertisement?.id,
      authorId: this.author.id
    });
    this.addComment(this.commentForm.value);
    this.commentService.saveComment(this.commentForm.value).subscribe();
  }

  addComment(comment: Comment) {
    this.event.emit();
  }

  ngOnInit(): void {
    this.getAuthor()
  }

  getAuthor(): void {
    this.authorService.getAuthorByEmail(this.loginService.userValue.username)
      .subscribe(author => this.author = author);
  }

  get text() {
    return this.commentForm.get("text");
  }
}
