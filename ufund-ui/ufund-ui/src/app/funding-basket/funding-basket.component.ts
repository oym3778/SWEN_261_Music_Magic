import { Component } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {
  currentBasket: Need[] = [];
  sum: number = 0;

  itemsPurchasedBool: boolean = false;
  proceed: boolean = false;

  constructor(private basketService: BasketService,
              private needService: NeedService) { }

  proceedToCheckOut(): void {
    if(this.currentBasket.length != 0){
      this.proceed = true;
    }else{
      this.proceed = false;
    }
  }
  
  itemsPurchased() {
    this.itemsPurchasedBool = true;
    this.currentBasket.forEach(need => this.needService.deleteNeed(need));
    this.currentBasket.forEach(need => this.removeFromBasket(need));
  }

  calcSum(): void {
    this.sum = 0;
    for (let i = 0; i < this.currentBasket.length; i++) {
      this.sum += this.currentBasket[i].price;
    }
  }

  getBasket(): void {
    this.basketService.getNeeds().subscribe(needs => {
      this.currentBasket = needs;
      this.calcSum();
      this.proceedToCheckOut();
    });
    this.itemsPurchasedBool = false;
  }

  getBasketUpdates(): void {
    this.basketService.basketMessanger$.subscribe(data => this.updateBasket(data));
    this.itemsPurchasedBool = false;
  }

  updateBasket(needs: Need[]): void {
    this.currentBasket = needs; 
    this.calcSum();
  }

  ngOnInit(): void {
    this.getBasket();
    this.getBasketUpdates();
    this.calcSum();
    this.itemsPurchasedBool = false;
    this.proceed = false;
  }

  removeFromBasket(need: Need): void {
    this.basketService.deleteNeed(need);
  }
}
