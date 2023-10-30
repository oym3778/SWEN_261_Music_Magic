import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Need } from '../need'
import { NeedService } from '../need.service';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-helper-view',
  templateUrl: './helper-view.component.html',
  styleUrls: ['./helper-view.component.css']
})

export class HelperViewComponent {
  needs: Need[] = []; //Array of all the needs to display.
  constructor(private basketService: BasketService,
              private needService: NeedService,
              private location: Location) { }


    // If the needs array is empty,
  // the method below returns the initial number (0).
  // if the needs array is not empty, the method below returns the highest
  // need id + 1.
  genId(needs: Need[]): number {
    return needs.length > 0 ? Math.max(...needs.map(need => need.id)) + 1 : 0;
  }

  //Update the stored needs array based on the values access by needService.
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  //Update needs array when the component is initialized. 
  ngOnInit(): void {
    this.getNeeds();
  }


  addToBasket(need: Need): void {
    this.needs = this.needs.filter(n => n !== need);
    this.basketService.addNeedToBasket(need).subscribe();
  }


  //Go to login page
  goBack(): void {
    this.location.back();
  }
}
