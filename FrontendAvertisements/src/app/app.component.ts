import {Component} from '@angular/core';
import {LoginService} from "./services/category/login.service";
import {Role} from "./models/Role";
import {AuthorizationUtils} from "./security/authorization-utils";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private loginService: LoginService, private authorizationUtils:AuthorizationUtils) {
  }

  logout() {
    this.loginService.logout();
  }

  isLogged() {
    return this.authorizationUtils.isLogged();
  }

  isAdmin() {
    return this.authorizationUtils.isAdmin();
  }

}
