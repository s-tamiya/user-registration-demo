import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse, HttpUserEvent, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { TokenService } from '../components/auth/token.service';
import { tap } from 'rxjs/operators';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterCeptor implements HttpInterceptor {

  constructor(private token: TokenService, private router: Router) {}

  intercept (req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    if (this.token.get() != null) {
      authReq = req.clone({ setHeaders: { Authorization: 'Bearer ' + this.token.get() } });
    }

    return next.handle(authReq)
      .pipe(
        tap(event => {
          if (event instanceof HttpResponse && event.ok) {
            this.token.save(event.headers.get(TOKEN_HEADER_KEY));
          }
        })
      );
  }
}
