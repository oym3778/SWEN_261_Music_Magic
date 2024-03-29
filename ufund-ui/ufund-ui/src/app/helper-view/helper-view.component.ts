import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { BasketService } from '../basket.service';
import { UserSessionService } from '../user-session.service';
import { Router } from '@angular/router';

import { FundingBasketComponent } from '../funding-basket/funding-basket.component';
import { debounceTime } from 'rxjs/operators';
import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-helper-view',
  templateUrl: './helper-view.component.html',
  styleUrls: ['./helper-view.component.css']
})

export class HelperViewComponent {
  private validated: boolean = false;

  constructor(
    private location: Location,
    private userSession: UserSessionService,
    private router: Router,
    private basketService: BasketService
  ) {
    this.userSession.getIsHelper().subscribe(val => {
      if (!val) this.router.navigate(['/login']);
      this.validated = val;
    })
  }

  //Go to login page
  goBack(): void {
    this.location.back();
  }

  getValidated(): boolean {
    return this.validated;
  }

}
