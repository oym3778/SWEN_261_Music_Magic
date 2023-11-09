import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserSessionService } from '../user-session.service';
import { isFormArray } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  displayError : boolean = false; 

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userSession: UserSessionService) { }

  username: string | undefined;
  password: string | undefined;

  login() {
    if (!this.username || !this.password) {
      return;
    }
    this.userSession.setPassword(this.password); 
    this.userSession.setUser(this.username); 

    if (this.username == "admin") {
      this.userSession.getIsAdmin().subscribe(val => {
        if(val) this.router.navigate(['/admin']);
        this.displayError = true; 
      });
    } 
    else if(this.username == "helper"){ 
      this.userSession.getIsHelper().subscribe(val => {
        if(val) this.router.navigate(['/helper']);
        this.displayError=true; 
      })
    }
  }
}
