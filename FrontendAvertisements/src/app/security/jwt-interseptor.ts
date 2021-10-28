import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {LoginService} from "../services/login.service";
import {Observable} from "rxjs";
import {AppSettings} from "../constants/AppSettings";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authenticationService: LoginService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const user = this.authenticationService.userValue;
    const isLoggedIn = user && user.token;
    const isApiUrl = request.url.startsWith(AppSettings.API_ENDPOINT);
    if (isLoggedIn && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer_${user.token}`
        }
      });
    }
    return next.handle(request);
  }
}
