import { Component } from '@angular/core';
import { BasketService } from '../basket.service';
import { Need } from '../need'

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  basket: Need[] = [];
  sum: number = 0;
  costCalculated: boolean = false;

  constructor(private basketService: BasketService) { }

  getBasket(): void {
    this.basketService.getNeeds().subscribe(needs => this.basket = needs);
  }

  ngOnInit(): void {
    this.getBasket();
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
    this.costCalculated = true;
  }

}
