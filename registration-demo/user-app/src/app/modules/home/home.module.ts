import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginFormComponent } from '../../shered/components/login-form/login-form.component';
import { LoginFormModule } from '../../shered/components/login-form/login-form.module';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './pages/home.component';

@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule,
    LoginFormModule
  ],
  declarations: [
    HomeComponent
  ]
})
export class HomeModule { }
