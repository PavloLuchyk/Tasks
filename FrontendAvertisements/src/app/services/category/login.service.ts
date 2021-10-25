import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../../constants/AppSettings";
import {AuthorizationModel} from "../../models/authorization-model";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn:"root"
})
export class LoginService{

  private authorSubject: BehaviorSubject<AuthorizationModel>;
  public author: Observable<AuthorizationModel>;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    this.authorSubject = new BehaviorSubject<AuthorizationModel>(JSON.parse(localStorage.getItem('user')!));
    this.author = this.authorSubject.asObservable();
  }

  public get userValue(): AuthorizationModel {
    return this.authorSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${AppSettings.API_ENDPOINT}/login`, { password,  username })
      .pipe(map(user => {
        localStorage.setItem('user', JSON.stringify(user));
        this.authorSubject.next(user);
        return user;
      }));
  }

  logout() {
    localStorage.removeItem('user');
    this.authorSubject.next(null!);
    this.router.navigate(['/']);
  }


}
