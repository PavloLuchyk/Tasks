import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Comment} from "../../models/comment";
import {CommentService} from "../../services/comment.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-comment-update',
  templateUrl: './comment-update.component.html',
  styleUrls: ['./comment-update.component.css']
})
export class CommentUpdateComponent implements OnInit {
  title?: string;
  message?: string;
  maxLength = 300;


  updateForm = new FormGroup({
    text: new FormControl(''),
    createDate: new FormControl(''),
    id: new FormControl(''),
    authorId: new FormControl(''),
    advertisementId: new FormControl(''),
    fullName: new FormControl('')
  });


  constructor(public dialogRef: MatDialogRef<CommentUpdateComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Comment) { }

  ngOnInit(): void {
    this.getComment();
  }

  getComment(): void {
      this.updateForm.setValue(this.data);
  }

  get text() {
    return this.updateForm.get('text');
  }
}
