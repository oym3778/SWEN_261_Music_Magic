import { Component } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';
import { Operation } from '../needs/needs.component';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {
  basket: Need[] = [];

  constructor(private basketService: BasketService) { }

  // Used within checkout to determine if the helper can proceed to checkout
  getFundingBasket(): Need[] {
    return this.basket;
  }

  getBasket(): void {
    this.basketService.getNeeds().subscribe(needs => this.basket = needs);
  }

  getBasketUpdates(): void {
    this.basketService.getUpdate().subscribe(data => this.changeBasketLocal(data.operation, data.need));
  }

  changeBasketLocal(operation: Operation, need: Need) : void {
    switch(operation){
      case Operation.ADD:
        if(need != null) this.basket.push(need);
        break;
      case Operation.DELETE:
        this.basket.filter(n => n !== need);
        break; 
    }

  }

  ngOnInit(): void {
    this.getBasket();
    this.getBasketUpdates(); 
  }

  addToBasket(need: Need): void {
    this.basketService.addNeedToBasket(need).subscribe();
    this.getBasket(); 
  }

  removeFromBasket(need: Need) : void {
    this.basket = this.basket.filter(n => n !== need);
    this.basketService.deleteNeed(need.id).subscribe();
  }
}
