import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

//Will be used to get whether the current user is an admin or helper, right now
//it doesn't property store the state, and so resets both to false if the page is refreshed
//will have to be changed later but works for testing perposes now.
export class UserSessionService {
  private isAdmin : boolean = false;
  private isHelper: boolean = false;  

  constructor() { }

  getIsAdmin() : boolean {
    return this.isAdmin; 
  }

  getIsHelper(): boolean {
    return this.isHelper; 
  }

  //Temporary functions for now, very insecure, data should be gotten from backend server not assigned locally. 
  //Will have to fix later when proper session management is figured out
  
  setIsAdmin(isAdmin: boolean) : void {
    this.isAdmin = isAdmin; 
  }

  setIsHelper(isHelper: boolean) : void {
    this.isHelper = isHelper; 
  }
}
