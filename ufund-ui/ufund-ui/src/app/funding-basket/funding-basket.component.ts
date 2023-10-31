import { Component } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {
  basket: Need[] = [];

  constructor(private basketService: BasketService) { }

  getBasket(): void {
    this.basketService.getNeeds().subscribe(needs => this.basket = needs);
  }

  ngOnInit(): void {
    this.getBasket();
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
