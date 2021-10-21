import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoryComponent} from "./category/category.component";
import {CategoryDetailsComponent} from "./category-details/category-details component";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";

const routes: Routes = [
  { path: '', redirectTo:'categories', pathMatch:"full"},
  { path: 'categories', component: CategoryComponent },
  { path: 'details/:id', component: CategoryDetailsComponent },
  { path: 'categories/create', component:CategoryCreateComponent},
  { path: 'register', component:AuthorRegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
