import { Component, Injectable, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';
import { Need } from '../need'
import { NeedService } from '../need.service'
import { BasketService } from '../basket.service';
import { Subscribable, Subscription } from 'rxjs';
import { UserSessionService } from '../user-session.service';


/**
 * Give the name to the associated html tag for this component and connect
 * the html and style sheets */
@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css']
})
/**
 * Defines the data and behavior of the NeedsComponent.
 */
export class NeedsComponent{
  needs: Need[] = []; //Array of all the needs to display.
  selectedNeed: Need | undefined;
  isHelper: boolean = false; 
  isAdmin: boolean = false; 
  filter: string = '';

  //Inject NeedService dependency.
  constructor(private needService: NeedService,
              private basketService: BasketService,
              private userSession: UserSessionService ) {

              }

  //Update the stored needs array based on the values access by needService.
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  getCurrentUser(): void {
    this.userSession.getIsHelper().subscribe(data => this.isHelper = data); 
    this.userSession.getIsAdmin().subscribe(data => this.isAdmin = data); 
  }

  checkFilter(need: Need): boolean {
    if(this.filter == '') return true; 
    return need.name.toLowerCase().startsWith(this.filter.toLowerCase()); 
  }

  //Update needs array when the component is initialized. 
  ngOnInit(): void {
    this.needService.needsMessanger$.subscribe(
      needs => {
        console.log(needs, "Needs recieved");
        this.needs = needs;
      }
    );
    this.needService.filterMessanger$.subscribe(
      filter => this.filter = filter
    );

    this.getNeeds();
    this.getCurrentUser(); 
  }

  delete(need: Need): void {
    this.needService.deleteNeed(need); 
  }

  moveToBasket(need: Need): void {
    this.basketService.addNeed(need);
  }

  onSelect(need: Need): void {
    this.selectedNeed = need; 
  }
}


