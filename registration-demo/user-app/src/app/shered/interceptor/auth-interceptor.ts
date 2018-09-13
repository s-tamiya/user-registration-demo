import { Injectable } from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpSentEvent, HttpHeaderResponse, HttpProgressEvent,
  HttpResponse, HttpUserEvent, HttpErrorResponse} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { TokenService } from '../components/auth/token.service';
import 'rxjs/add/operator/do';

@Injectable()
export class AuthInterCeptor implements HttpInterceptor {

  constructor() {}

  intercept (req: HttpRequest, next: HttpHandler): Observable<HttpUserEvent> {}
}
