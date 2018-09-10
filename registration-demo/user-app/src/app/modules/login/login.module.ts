import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginFormModule } from '../../shered/components/login-form/login-form.module';
import { LoginComponent } from './pages/login.component';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    LoginFormModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
