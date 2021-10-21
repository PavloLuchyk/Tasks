import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {CategoryComponent} from "./category/category.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CategoryDetailsComponent} from "./category-details/category-details component";
import {MessageComponent} from "./message/message.component";
import {AppRoutingModule} from "./app-routing.module";
import { HttpClientModule } from "@angular/common/http";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";

@NgModule({
  declarations: [
    AppComponent,
    CategoryComponent,
    CategoryDetailsComponent,
    MessageComponent,
    CategoryCreateComponent,
    AuthorRegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
