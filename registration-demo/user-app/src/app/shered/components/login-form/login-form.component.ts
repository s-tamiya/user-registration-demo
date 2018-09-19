import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';

import { LoginService } from './login.service';
import { TokenService } from '../auth/token.service';
import { User } from '../../models/user';

const values = {
  signup : {
    title: 'Please Sign Up',
    button: 'Sign Up',
  },
  signin : {
    title: 'Please Sign In',
    button: 'Sign In',
  }
}

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  providers: [ LoginService ]
})
export class LoginFormComponent implements OnInit {

  public name: string;　
  public email: string;
  public password: string;
  @Input() isSignup: boolean;
  public messages = {};

  constructor(
    private loginService: LoginService,
    private tokenService: TokenService
  ) {
  }

  ngOnInit() {
    this.name = '';
    this.email = '';
    this.password = '';

    this.messages = this.isSignup ? values.signup: values.signin;
  }

  signup(): void {
    this.loginService.register(this.name, this.email, this.password)
      .subscribe(data => console.log(data));
  }

  login(): void {
    this.loginService.login(this.email, this.password)
      .subscribe(result => console.log(`LoginFormComponent.login result: ${result}`));
  }
}
