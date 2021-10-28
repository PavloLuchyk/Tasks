import {Component} from '@angular/core';
import {LoginService} from "./services/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private loginService: LoginService) {
  }

  logout() {
    this.loginService.logout();
  }

  isLogged() {
    return this.loginService.isLogged;
  }

  isAdmin() {
    return this.loginService.isAdmin;
  }

  get id() {
    const user = this.loginService.userValue;
    if (user) {
      return user.id;
    } else {
      return -1;
    }
  }

}
