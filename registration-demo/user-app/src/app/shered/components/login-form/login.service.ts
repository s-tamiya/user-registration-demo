import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from '../../models/user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type' : 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private BASE_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  register (name: string, email: string, password: string) : Observable<User> {
    const credentials = { name: name, email: email, password: password };
    return this.http
      .post<User>(`${this.BASE_URL}/user/register`, credentials);
  }

  antenticate (email: string, password: string) : Observable<HttpResponse<any>> {
    const credentials = { email: email, password: password };
    let httpParams = new HttpParams()
      .append("email", email)
      .append("password", password);

    return this.http.post<HttpResponse<any>>(`${this.BASE_URL}/login`, httpParams, {observe: 'response'});
  }
}
