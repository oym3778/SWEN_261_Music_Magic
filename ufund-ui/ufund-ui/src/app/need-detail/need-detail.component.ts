import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Need } from '../need';
import { NeedService } from '../need.service';
import { UserSessionService } from '../user-session.service';



@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  @Input() need?: Need; 

  private isAdmin: boolean = false; 

  constructor(private needService: NeedService,
              private userSession: UserSessionService) {}

  saveNeed(): void {
    if(this.need){
      this.needService.updateNeed(this.need).subscribe();
    }
  }

  ngOnInit(): void {
    this.userSession.getIsAdmin().subscribe(bool => this.isAdmin = bool); 
  }

  getIsAdmin(): boolean {
    return this.isAdmin; 
  }
}
