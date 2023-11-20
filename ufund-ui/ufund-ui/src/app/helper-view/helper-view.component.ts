import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { BasketService } from '../basket.service';
import { UserSessionService } from '../user-session.service';
import { Router } from '@angular/router';
import { FundingBasketComponent } from '../funding-basket/funding-basket.component';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-helper-view',
  templateUrl: './helper-view.component.html',
  styleUrls: ['./helper-view.component.css']
})

export class HelperViewComponent {
  private validated: boolean = false;
  // currentBasket: Need[] = [];


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
  // ngOnChanges(){
  //   this.getBasket();
  // }

  // getBasket(): void {
  //   this.basketService.getNeeds().subscribe(needs => this.currentBasket = needs);
  // }

  //Go to login page
  goBack(): void {
    this.location.back();
  }

  getValidated(): boolean {
    return this.validated;
  }

  // proceedToCheckout(): void {
  //   // if there are needs in the funding basket, the helper may proceed to checkout
  //   // This is probably bad practice, calling the GET request again after its already been called. 
  //   // TODO refactor this...
  //   this.getBasket();
  //   debounceTime(300);

  //   if (this.currentBasket.length != 0) {
  //     this.router.navigate(['/checkout']);
  //   }

  //   // otherwise the button should be transparent and do nothing when clicked

  // }
}
