import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegisterRoutingModule } from './register-routing.module';
import { LoginFormModule } from '../../shered/components/login-form/login-form.module';
import { RegisterComponent } from './pages/register.component';

@NgModule({
  imports: [
    CommonModule,
    RegisterRoutingModule,
    LoginFormModule
  ],
  declarations: [RegisterComponent]
})
export class RegisterModule { }
