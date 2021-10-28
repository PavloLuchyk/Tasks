import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthorService} from "../../services/author.service";
import {Router} from "@angular/router";
import {LoginService} from "../../services/login.service";
import {AuthorView} from "../../models/author-view";

@Component({
  selector: 'app-author-update',
  templateUrl: './author-update.component.html',
  styleUrls: ['./author-update.component.css']
})
export class AuthorUpdateComponent implements OnInit {

  hide = true;
  authorView?: AuthorView;

  authorForm = new FormGroup({
    id: new FormControl(''),
    firstName: new FormControl('',
      [Validators.required, Validators.pattern("^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?")]
    ),
    lastName: new FormControl('',
      [Validators.required, Validators.pattern("^[A-Z][a-z]{1,20}([-][A-Z][a-z]{1,20})?")]
    ),
    email: new FormControl('',
      {
        updateOn: "blur",
        validators: [Validators.required, Validators.pattern("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")],
        asyncValidators: [this.authorService.getUniqueValidator()]
      }
    ),
    password: new FormControl('', Validators.required),
    role: new FormControl(''),
    createTime: new FormControl('')
  });

  constructor(private authorService: AuthorService,
              private loginService: LoginService,
              private router: Router) { }

  get email() {
    return this.authorForm.get("email");
  }

  ngOnInit(): void {
    this.getAuthor();
  }

  onSubmit(): void  {
    this.authorService.updateAuthor(this.authorForm.value)
      .subscribe();
    this.router.navigate([`/author/${this.authorView?.id}`])
      .then();
  }


  getAuthor() {
    this.authorService.getAuthor(this.loginService.userValue.id)
      .subscribe(author => {
        this.authorView = author;
        this.authorForm.patchValue({
          id: author.id,
          firstName: author.firstName,
          lastName: author.lastName,
          email: author.email,
          role: author.role,
          createTime: author.createDate
        })
      });
  }


}
