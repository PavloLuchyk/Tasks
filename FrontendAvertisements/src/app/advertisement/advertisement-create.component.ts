import {Component, Input, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AdvertisementService} from "../services/advertisement.service";
import {LoginService} from "../services/category/login.service";
import {Category} from "../models/category";
import {AuthorView} from "../models/author-view";
import {ActivatedRoute} from "@angular/router";
import {CategoryService} from "../services/category/category.service";
import {AuthorService} from "../services/category/author.service";

@Component({
  selector:'advertisement-create',
  templateUrl: 'advertisement-create.component.html'
})
export class AdvertisementCreateComponent implements OnInit{


    category?: Category;
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
              private categoryService: CategoryService) {
    this.category = this.advertisementService.category;
  }

  onSubmit() {
    if (this.category !== undefined) {
      this.advertisementForm.patchValue({category: this.category!});
    }
    this.advertisementForm.patchValue({author: this.author!});
    this.advertisementService.saveAdvertisement(this.advertisementForm.value).subscribe();
  }

  ngOnInit(): void {
    this.categoryService.getCategories()
      .subscribe(categories => this.categories = categories);
    this.authorService.getAuthorByEmail(this.loginService.userValue.username)
      .subscribe(author => this.author = author);
  }
}
