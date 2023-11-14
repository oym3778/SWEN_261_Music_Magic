import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserSessionService } from '../user-session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

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

    if (this.username == "admin") {
      this.userSession.setIsAdmin(true); 
      this.router.navigate(['/admin']);
    } else {
      // TO-DO
      // CREATE A VIEW.ROUTE FOR THE NEEDS VIEW
      this.userSession.setIsHelper(true); 
      this.router.navigate(['/helper']);
    }

  }
}
