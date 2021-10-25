import {Component} from "@angular/core";
import {LoginService} from "../services/category/login.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector:"login-component",
  templateUrl:"login.component.html"
})
export class LoginComponent {

  loginForm = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })

  constructor(private loginService: LoginService, private router: Router) {

  }

  onSubmit() {
    this.loginService.login(
      this.loginForm.get('email')?.value,
      this.loginForm.get('password')?.value
    ).subscribe();
    this.router.navigate(['/']);
  }

  getUser(){
    console.log(localStorage.getItem('user'));
  }

}
