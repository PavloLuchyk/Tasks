import {Injectable} from "@angular/core";
import {AppSettings} from "../../constants/AppSettings";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {AuthorView} from "../../models/author-view";
import {AuthorRegistration} from "../../models/author-registration";
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {catchError, map} from "rxjs/operators";
import {LoginService} from "./login.service";

@Injectable({
    providedIn:'root'
  }
)
export class AuthorService {
  private authorUrl: string = `${AppSettings.API_ENDPOINT}/author`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  getAuthors(): Observable<AuthorView[]> {
    return this.http.get<AuthorView[]>(this.authorUrl);
  }

  getAuthor(id:number): Observable<AuthorView> {
    const url = `${this.authorUrl}/${id}`;
    return this.http.get<AuthorView>(url);
  }

  getAuthorByEmail(email: string): Observable<AuthorView> {
    const url = `${this.authorUrl}/email/${email}`;
    return this.http.get<AuthorView>(url);
  }

  updateAuthor(author: AuthorRegistration): Observable<any> {
    const url = `${this.authorUrl}/${author.id}`;
    return this.http.put(url, author, this.httpOptions);
  }

  deleteAuthor(id:number): Observable<Object> {
    const url = `${this.authorUrl}/${id}`;
    return this.http.delete(url,this.httpOptions);
  }

  saveAuthor(author: AuthorRegistration) : Observable<AuthorRegistration> {
    const url = `${this.authorUrl}/add`;
    return this.http.post<AuthorRegistration>(url, author, this.httpOptions);
  }

  getAuthorsInPages(pageSize: number, pageNumber:number): Observable<AuthorView[]> {
    const url = `${this.authorUrl}/pages/${pageSize}/${pageNumber}`;
    return this.http.get<AuthorView[]>(url);
  }

  getTotalNumberOfPages(pageSize: number): Observable<number> {
    const url = `${this.authorUrl}/pages/${pageSize}`;
    return this.http.get<number>(url);
  }

  isUnique(email: string): Observable<boolean> {
    const url = `${this.authorUrl}/check`;
    let param = new HttpParams().set("email", email);
    return this.http.get<boolean>(url, {params: param});
  }

  isIdentical(password: string, id: number): Observable<boolean> {
    const url =`${this.authorUrl}/check/password`
    return this.http.post<boolean>(url, {password, id}, this.httpOptions)
  }

  getUniqueValidator() : AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const user = this.loginService.userValue;
      if (user) {
        if (user.username == control.value){
          return of(null);
        }
      }
      return this.isUnique(control.value).pipe(
        map(isTaken => (!isTaken ? { exists: true } : null)),
        catchError(() => of(null)));
    }
  }

  constructor(private http: HttpClient,
             private loginService: LoginService) {
  }
}
