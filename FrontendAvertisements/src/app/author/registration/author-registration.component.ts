import {Component} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthorService} from "../../services/category/author.service";
import {AuthorRegistration} from "../../models/author-registration";

@Component({
  selector:"author-registration",
  templateUrl:"author-registration.component.html"
})
export class AuthorRegistrationComponent {

  private author?:AuthorRegistration;

    authorForm = new FormGroup({
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
      password: new FormControl('')
    });

  onSubmit(): void {
    this.authorService.saveAuthor(this.authorForm.value)
      .subscribe(author =>this.author =author);
    this.router.navigateByUrl("/categories");
  }

  get email() {
      return this.authorForm.get("email");
  }

  constructor(private authorService: AuthorService, private router: Router) {
  }
}
