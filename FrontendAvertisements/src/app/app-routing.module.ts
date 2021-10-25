import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoryComponent} from "./category/category.component";
import {CategoryDetailsComponent} from "./category-details/category-details component";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";
import {LoginComponent} from "./login/login.component";
import {AdvertisementComponent} from "./advertisement/advertisement.component";
import {AdvertisementCreateComponent} from "./advertisement/advertisement-create.component";
import {AdvertisementByCategoryComponent} from "./advertisement/advertisement-by-category.component";
import {CommentComponent} from "./comment/comment.component";

const routes: Routes = [
  { path: '', redirectTo:'categories', pathMatch:"full"},
  { path: 'categories', component: CategoryComponent },
  { path: 'details/:id', component: CategoryDetailsComponent },
  { path: 'categories/create', component:CategoryCreateComponent},
  { path: 'register', component:AuthorRegistrationComponent},
  { path: 'login', component: LoginComponent},
  { path: 'advertisement', component:AdvertisementComponent},
  { path: 'advertisement/create', component:AdvertisementCreateComponent},
  { path: 'advertisement/category/:id', component: AdvertisementByCategoryComponent},
  { path: 'advertisement/:id', component: CommentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
