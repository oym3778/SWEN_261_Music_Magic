import { Component, Injectable, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';
import { Need } from '../need'
import { NeedService } from '../need.service'
import { BasketService } from '../basket.service';
import { Subscribable, Subscription } from 'rxjs';
import { UserSessionService } from '../user-session.service';

export enum Operation {
  ADD, 
  DELETE,
  FILTER
}

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
  isHelper: boolean = false; 
  isAdmin: boolean = false; 
  filter: string = '';

  //Inject NeedService dependency.
  constructor(private needService: NeedService,
              private basketService: BasketService,
              private userSession: UserSessionService ) { }

  //Update the stored needs array based on the values access by needService.
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  getAddedNeed(): void {
    this.needService.getUpdate().subscribe(data => {
      this.updateNeeds(data);
      console.log(data);
    });
    this.needService.getFilterMessanger().subscribe(filter => this.filter = filter);
  }

  getCurrentUser(): void {
    this.userSession.getIsHelper().subscribe(data => this.isHelper = data); 
    this.userSession.getIsAdmin().subscribe(data => this.isAdmin = data); 
  }

  checkFilter(need: Need): boolean {
    if(this.filter == '') return true; 
    return need.name.toLowerCase().startsWith(this.filter.toLowerCase()); 
  }

  updateNeeds(needs: Need[]) : void {
    this.needs = needs; 
    console.log(needs);
  }

  //Update needs array when the component is initialized. 
  ngOnInit(): void {
    this.getNeeds();
    this.getCurrentUser(); 
    this.getAddedNeed(); 
  }

  delete(need: Need): void {
    this.needService.deleteNeed(need); 
  }

  moveToBasket(need: Need): void {
    this.basketService.addNeed(need);
  }
}


