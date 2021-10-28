import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {LoginService} from "../services/category/login.service";
import {Role} from "../models/Role";

@Injectable({
  providedIn:'root'
})
export class OwnerGuard implements CanActivate {

  constructor(private loginService:LoginService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const user = this.loginService.userValue;

    if (user) {
      const id = Number(route.paramMap.get("id"));
      if (user.role===Role.USER&&user.id !== id) {
        this.router.navigate(['/']);
        return false;
      }

      return true;
    }

    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

}
