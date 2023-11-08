import { Component } from '@angular/core';
import { UserSessionService } from '../user-session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrls: ['./admin-view.component.css']
})
export class AdminViewComponent {

  constructor(
    private userSession: UserSessionService,
    private router: Router
  ){
    this.userSession.getIsAdmin().subscribe(val => {
     if(!val) this.router.navigate(['/login']);   
    })
  }

}
