import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

//Will be used to get whether the current user is an admin or helper, right now
//it doesn't property store the state, and so resets both to false if the page is refreshed
//will have to be changed later but works for testing perposes now.
export class UserSessionService {
  private user = ""; 
  private password = ""; 
  private loginUrl = "http://localhost:8080/auth/login"

  constructor(
    private http: HttpClient) { }

  getIsAdmin() : Observable<boolean> {
    return this.http.post<boolean>(this.loginUrl, "dummy"); 
  }

  getIsHelper(): Observable<boolean> {
    return this.http.post<boolean>(this.loginUrl, "dummy"); 
  }
}
