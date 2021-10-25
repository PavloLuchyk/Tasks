import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {CategoryComponent} from "./category/category.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CategoryDetailsComponent} from "./category-details/category-details component";
import {MessageComponent} from "./message/message.component";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";
import {LoginComponent} from "./login/login.component";
import {JwtInterceptor} from "./security/jwt-interseptor";
import {AdvertisementComponent} from "./advertisement/advertisement.component";
import {AdvertisementCreateComponent} from "./advertisement/advertisement-create.component";
import {AdvertisementByCategoryComponent} from "./advertisement/advertisement-by-category.component";
import {Comment} from "@angular/compiler";
import {CommentComponent} from "./comment/comment.component";
import {CommentCreateComponent} from "./comment/comment-create.component";
import {AdvertisementUpdateComponent} from "./advertisement/advertisement-update.component";

@NgModule({
  declarations: [
    AppComponent,
    CategoryComponent,
    CategoryDetailsComponent,
    MessageComponent,
    CategoryCreateComponent,
    AuthorRegistrationComponent,
    LoginComponent,
    AdvertisementComponent,
    AdvertisementCreateComponent,
    AdvertisementByCategoryComponent,
    CommentComponent,
    CommentCreateComponent,
    AdvertisementUpdateComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
