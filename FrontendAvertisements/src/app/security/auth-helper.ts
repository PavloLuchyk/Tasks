import {LoginService} from "../services/category/login.service";

export class AuthHelper {
  constructor(private loginService: LoginService) {

  }

  isAdmin(): boolean {
    return this.loginService.isAdmin;
  }

  isLogged(): boolean {
    return this.loginService.isLogged;
  }


}
