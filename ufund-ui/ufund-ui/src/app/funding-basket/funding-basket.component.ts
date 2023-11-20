import { Component } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';
import { Operation } from '../needs/needs.component';
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
    if (this.currentBasket.length != 0) {
      this.itemsPurchasedBool = true;
      for(let i = 0; i < this.currentBasket.length; i++)
      {
        this.needService.deleteNeedSubjects(this.currentBasket[i]);
      }
      this.currentBasket.forEach(need => this.removeFromBasket(need));
      
    }

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
    this.basketService.getUpdate().subscribe(data => this.changeBasketLocal(data.operation, data.need));
    this.itemsPurchasedBool = false;
  }

  changeBasketLocal(operation: Operation, need: Need): void {
    switch (operation) {
      case Operation.ADD:
        if (need != null) this.currentBasket.push(need);
        this.calcSum();
        break;
      case Operation.DELETE:
        this.currentBasket = this.currentBasket.filter(n => n !== need);
        break;
    }
    this.itemsPurchasedBool = false;
  }

  ngOnInit(): void {
    this.getBasket();
    this.getBasketUpdates();
    this.calcSum();
    this.itemsPurchasedBool = false;
    this.proceed = false;
  }

  addToBasket(need: Need): void {
    this.basketService.addNeedToBasket(need).subscribe();
    this.getBasket();
    this.itemsPurchasedBool = false;
  }

  removeFromBasket(need: Need): void {
    this.basketService.deleteNeed(need).subscribe();
    this.calcSum();
    this.itemsPurchasedBool = false;
  }
}
