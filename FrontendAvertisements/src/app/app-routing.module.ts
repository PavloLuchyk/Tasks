import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoryComponent} from "./category/component/category.component";
import {CategoryUpdateComponent} from "./category/category-update/category-update.component";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";
import {LoginComponent} from "./login/login.component";
import {AdvertisementComponent} from "./advertisement/all/advertisement.component";
import {AdvertisementCreateComponent} from "./advertisement/advertisement-create/advertisement-create.component";
import {AdvertisementByCategoryComponent} from "./advertisement/advertisement-by-category/advertisement-by-category.component";
import {CommentComponent} from "./comment/component/comment.component";
import {AdvertisementUpdateComponent} from "./advertisement/advertisement-update/advertisement-update.component";
import {AuthorAllComponent} from "./author/author-all/author-all.component";
import {AuthorDetailsComponent} from "./author/author-details/author-details.component";
import {AuthorUpdateComponent} from "./author/author-update/author-update.component";
import {AdvertisementByAuthorComponent} from "./advertisement/advertisement-by-author/advertisement-by-author.component";
import {AuthGuard} from "./security/auth-guard";
import {Role} from "./models/Role";
import {OwnerGuard} from "./security/owner-guard";

const routes: Routes = [
  { path: '', redirectTo:'categories', pathMatch:"full"},
  { path: 'categories', component: CategoryComponent },
  { path: 'category/:id', component: CategoryUpdateComponent,
    canActivate:[AuthGuard], data: {roles:[Role.ADMIN]} },
  { path: 'categories/create', component:CategoryCreateComponent,
    canActivate:[AuthGuard], data: {roles:[Role.ADMIN]}
  },
  { path: 'register', component:AuthorRegistrationComponent},
  { path: 'login', component: LoginComponent},
  { path: 'advertisement', component:AdvertisementComponent},
  { path: 'advertisement/create', component:AdvertisementCreateComponent},
  { path: 'advertisement/category/:id', component: AdvertisementByCategoryComponent},
  { path: 'advertisement/:id', component: CommentComponent},
  { path: 'advertisement/update/:id', component:AdvertisementUpdateComponent},
  { path: 'author', component: AuthorAllComponent},
  { path: 'author/:id', component: AuthorDetailsComponent},
  { path: 'author/update/:id', component: AuthorUpdateComponent,
    canActivate:[AuthGuard, OwnerGuard], data: {roles:[Role.ADMIN, Role.USER]}},
  { path: 'advertisement/author/:id', component: AdvertisementByAuthorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
