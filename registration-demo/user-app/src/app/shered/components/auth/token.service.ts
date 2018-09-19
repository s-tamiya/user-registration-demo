import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  public save(token: string): void {
    this.clear();
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public get(): string {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public getAuthToken(): string {
    return 'Bearer ' + this.get();
  }

  public clear() {
    window.sessionStorage.removeItem(TOKEN_KEY);
  }
}
