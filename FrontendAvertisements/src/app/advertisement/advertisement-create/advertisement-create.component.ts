import {Component, Input, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AdvertisementService} from "../../services/advertisement.service";
import {LoginService} from "../../services/category/login.service";
import {Category} from "../../models/category";
import {AuthorView} from "../../models/author-view";
import {ActivatedRoute, Router} from "@angular/router";
import {CategoryService} from "../../services/category/category.service";
import {AuthorService} from "../../services/category/author.service";
import {delay} from "rxjs/operators";

@Component({
  selector:'advertisement-create',
  templateUrl: 'advertisement-create.component.html',
  styleUrls: ['advertisement.create.component.css']
})
export class AdvertisementCreateComponent implements OnInit{


    category?: Category;
    selected?: Category;
    author?: AuthorView;
    categories: Category[] = [];

  advertisementForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    category: new FormControl('', Validators.required),
    author: new FormControl('', Validators.required)
  })

  constructor(private advertisementService: AdvertisementService,
              private loginService: LoginService,
              private authorService: AuthorService,
              private categoryService: CategoryService,
              private router: Router) {
  }

  onSubmit() {
    if (!this.category) {
      this.category = this.categories.find(x => x.name === this.advertisementForm.get('category')?.value);
    }
    this.advertisementForm.patchValue({author: this.author!,
                                            category: this.category!});
    this.advertisementService.saveAdvertisement(this.advertisementForm.value)
      .subscribe(adv => delay(1000));
    this.router.navigateByUrl(`/advertisement/category/${this.category?.id}`)
      .then();
  }

  ngOnInit(): void {
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
    this.authorService.getAuthorByEmail(this.loginService.userValue.username)
      .subscribe(author => this.author = author);
    this.advertisementService.category?.subscribe(
      category => {
        this.category=category;
      }
    )
    console.log(this.category);
  }
}
