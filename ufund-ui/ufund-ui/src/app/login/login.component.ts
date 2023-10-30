import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router) { }

  username: string | undefined;
  password: string | undefined;

  login() {
    if (!this.username || !this.password) {
      return;
    }

    if (this.username == "admin") {
      this.router.navigate(['/needs']);
    } else {
      // TO-DO
      // CREATE A VIEW.ROUTE FOR THE NEEDS VIEW
      this.router.navigate(['/helper']);
    }

  }
}
