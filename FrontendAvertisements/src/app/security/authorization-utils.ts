import {Role} from "../models/Role";
import {LoginService} from "../services/category/login.service";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn:"root"
})
export class AuthorizationUtils {

  constructor(private loginService:LoginService) {
  }

  isLogged() {
    const user = this.loginService.userValue;
    if (user && user.token) {
      return true;
    }
    return false;
  }

  isAdmin() {
    const user = this.loginService.userValue;
    if (this.isLogged()) {
      if (user.role === Role.ADMIN) {
        return false;
      }
    }
    return false;
  }
}
