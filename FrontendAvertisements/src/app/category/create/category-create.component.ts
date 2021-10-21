import {Component} from "@angular/core";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {CategoryService} from "../../services/category/category.service";
import {Category} from "../category";
import {Router} from "@angular/router";
import {map} from "rxjs/operators";
import {compareSegments} from "@angular/compiler-cli/src/ngtsc/sourcemaps/src/segment_marker";

@Component({
  selector:"category-create",
  templateUrl:"category-create.component.html"
})
export class CategoryCreateComponent {

  private category?: Category;

  categoryForm = new FormGroup({
      name: new FormControl('',
        {
          updateOn: "blur",
          validators: [Validators.required],
          asyncValidators:[this.categoryService.getUniqueValidator()]
        }),
      description: new FormControl('')
  });

  onSubmit(): void {
    this.categoryService.saveCategory(this.categoryForm.value)
      .subscribe(category =>this.category =category);
    this.router.navigateByUrl("/categories");
  }

  get name() {
    return this.categoryForm.get("name");
  }

  constructor(private categoryService: CategoryService, private router: Router){
  }
}
