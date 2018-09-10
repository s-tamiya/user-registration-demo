import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';

import { LoginService } from './login.service';
import { User } from '../../models/user';

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
  //public isSignUp: boolean;
  //@Input() hero: Hero;これで渡せたはず...

  constructor(
    private loginService: LoginService,
    private route: ActivatedRoute// これを使ってパラメータの受け渡し？
  ) {
  }

  ngOnInit() {
    this.name = '';
    this.email = '';
    this.password = '';
    alert(isSignUp);
  }

  login (): void{
    alert(this.loginService.test('test'));
  }
}
