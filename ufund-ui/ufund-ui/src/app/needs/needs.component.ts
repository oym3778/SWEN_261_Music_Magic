import { Component } from '@angular/core';
import { Need } from '../need'
import { NeedService } from '../need.service';

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
export class NeedsComponent {
  needs: Need[] = []; //Array of all the needs to display.

  //Inject NeedService dependency.
  constructor(private needService: NeedService) {}

  //Update the stored needs array based on the values access by needService.
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  //Update needs array when the component is initialized. 
  ngOnInit(): void {
    this.getNeeds();
  }

}


