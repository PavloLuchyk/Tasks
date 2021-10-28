import { Injectable } from "@angular/core";
import {Category} from "../models/category";
import {Observable, of} from "rxjs";
import {MessageService} from "./message.service"
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {AppSettings} from "../constants/AppSettings";

@Injectable({
  providedIn:'root'
  }
)
export class CategoryService {

  private categoriesUrl: string = `${AppSettings.API_ENDPOINT}/category`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesUrl);
  }

  getCategory(id: number): Observable<Category> {
    const url = `${this.categoriesUrl}/${id}`;
    return this.http.get<Category>(url);
  }

  updateCategory(category:Category): Observable<any> {
    const url = `${this.categoriesUrl}/${category.id}`;
    return this.http.put(url, category, this.httpOptions);
  }

  deleteCategory(id: number): Observable<Object> {
    const url = `${this.categoriesUrl}/${id}`;
    return this.http.delete(url, this.httpOptions);
  }

  saveCategory(category:Category): Observable<Category> {
    const url = `${this.categoriesUrl}/add`;
    return this.http.post<Category>(url, category, this.httpOptions);
  }

  getCategoryInPages(pageSize:number, pageNumber:number): Observable<Category[]> {
    const url = `${this.categoriesUrl}/pages/${pageSize}/${pageNumber}`;
    return this.http.get<Category[]>(url);
  }

  getTotalAmountOfPages(pageSize:number): Observable<number> {
    const url = `${this.categoriesUrl}/pages/${pageSize}`;
    return this.http.get<number>(url);
  }

  isUnique(name:string): Observable<boolean> {
    const url = `${this.categoriesUrl}/check`;
    let param = new HttpParams().set("name", name);
    return this.http.get<boolean>(url, {params: param});
  }

  getUniqueValidator() : AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.isUnique(control.value).pipe(
        map(isTaken => (!isTaken ? { notUnique: true } : null)),
        catchError(() => of(null)));
    }
  }

  constructor(private messageService: MessageService, private http: HttpClient) { }
}
