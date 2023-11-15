import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

//Will be used to get whether the current user is an admin or helper, right now
//it doesn't property store the state, and so resets both to false if the page is refreshed
//will have to be changed later but works for testing perposes now.
export class UserSessionService {
  private user = localStorage.getItem("user"); 
  private password = localStorage.getItem("password"); 
  private loginUrl = "http://localhost:8080/auth/login"

  constructor(
    private http: HttpClient) { }

  private validateUser(): Observable<boolean> {
    return this.http.post<boolean>(this.loginUrl, [this.user, this.password]);
  }

  getIsAdmin() : Observable<boolean> {
    if(this.user != "admin") return of(false);
    return this.validateUser(); 
  }

  getIsHelper(): Observable<boolean> {
    if(this.user != "helper") return of(false); 
    return this.validateUser(); 
  }

  

  setPassword(password : string): void {
    localStorage.setItem("password", password); 
    this.password = password; 
  }

  setUser(user: string): void {
    localStorage.setItem("user", user); 
    this.user = user; 
  }
}
