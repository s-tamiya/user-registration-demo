import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { PageNotFoundComponent } from './shered/components/page-not-found/page-not-found.component';

const routes: Routes = [
        { path : '', pathMatch: 'full', redirectTo: 'home' },
        //{ path: '**', component:  PageNotFoundComponent },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot( routes, { enableTracing: true } )
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
