import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {CategoryComponent} from "./category/component/category.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CategoryDetailsComponent} from "./category-details/category-details component";
import {MessageComponent} from "./message/message.component";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CategoryCreateComponent} from "./category/create/category-create.component";
import {AuthorRegistrationComponent} from "./author/registration/author-registration.component";
import {LoginComponent} from "./login/login.component";
import {JwtInterceptor} from "./security/jwt-interseptor";
import {AdvertisementComponent} from "./advertisement/all/advertisement.component";
import {AdvertisementCreateComponent} from "./advertisement/advertisement-create/advertisement-create.component";
import {AdvertisementByCategoryComponent} from "./advertisement/advertisement-by-category/advertisement-by-category.component";
import {Comment} from "@angular/compiler";
import {CommentComponent} from "./comment/component/comment.component";
import {CommentCreateComponent} from "./comment/comment-create.component";
import {AdvertisementUpdateComponent} from "./advertisement/advertisement-update/advertisement-update.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenuComponent } from './author/menu/menu.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {MatMenuModule} from "@angular/material/menu";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {MatSelectModule} from "@angular/material/select";
import {MatDialogModule} from "@angular/material/dialog";
import {MDBBootstrapModule} from "angular-bootstrap-md";
import { DialogComponent } from './dialog/dialog.component';
import { AuthorAllComponent } from './author/author-all/author-all.component';
import { AuthorDetailsComponent } from './author/author-details/author-details.component';
import { ProfileComponent } from './author/profile/profile.component';
import { AuthorUpdateComponent } from './author/author-update/author-update.component';
import { AdvertisementByAuthorComponent } from './advertisement/advertisement-by-author/advertisement-by-author.component';

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
    AdvertisementUpdateComponent,
    MenuComponent,
    DialogComponent,
    AuthorAllComponent,
    AuthorDetailsComponent,
    ProfileComponent,
    AuthorUpdateComponent,
    AdvertisementByAuthorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    FlexModule,
    FlexLayoutModule,
    MatSelectModule,
    MatDialogModule,
    MDBBootstrapModule.forRoot()
  ],
  exports: [
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
