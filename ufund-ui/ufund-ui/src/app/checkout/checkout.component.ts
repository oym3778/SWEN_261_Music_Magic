import { Component, Input } from '@angular/core';
import { BasketService } from '../basket.service';
import { Need } from '../need';
import { debounceTime } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {

// Once HTML refactored this TS file should be refactored...






  
  // I want basket to be updated from the funding basket itself, parent child 
  @Input() basket: Need[] = [];
  sum: number = 0;

  // Transition from check out order to the actual checkout
  costCalculated: boolean = false;

  // procceed to the next screen
  proceed: boolean = true;

  constructor(
    private basketService: BasketService, 
    private router: Router,
    ) { }

  // getBasket(): void {
  //   this.basketService.getNeeds().subscribe(needs => this.basket = needs);
  // }
  // ngOnChanges(){
  //   this.getBasket();
  //   console.log(this.basket);
    
  // }
  ngOnInit(): void {
    // this.getBasket();
    // if(this.basket != null){
    //   for (let i = 0; i < this.basket.length; i++) {
    //     this.sum += this.basket[i].price;
    //   }
    // }
    // while(this.displaySum == false){
    //   for (let i = 0; i < this.basket.length; i++) {
    //     this.sum += this.basket[i].price;
    //   }

    //   if(this.sum == -1){
    //   }else{
    //     this.displaySum = true;
    //   }
    // }
    

  }

  calcSum(): void {
    for (let i = 0; i < this.basket.length; i++) {
      this.sum += this.basket[i].price;
    }
    // this.costCalculated = true;
  }


  proceedToCheckout(): void {
    // if there are needs in the funding basket, the helper may proceed to checkout
    // This is probably bad practice, calling the GET request again after its already been called. 
    // TODO refactor this...
    // this.getBasket();
    debounceTime(300);
    if(this.basket.length != 0){
      this.router.navigate(['/checkout']);
      this.proceed = false;
    }

    // otherwise the button should be transparent and do nothing when clicked

  }
}
