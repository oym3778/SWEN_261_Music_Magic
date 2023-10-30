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

  // tempNeed: Need = {
  //   id: 1,
  //   name: 'testNeed',
  //   price: 2,
  //   quantity: 2,
  // };

  //Inject NeedService dependency.
  constructor(private needService: NeedService) { }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(needs: Need[]): number {
    return needs.length > 0 ? Math.max(...needs.map(need => need.id)) + 1 : 11;
  }

  //Update the stored needs array based on the values access by needService.
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  //Update needs array when the component is initialized. 
  ngOnInit(): void {
    this.getNeeds();
  }

  
  add(nameH: string, price: string, quantity: string): void {
    // need to do other checks
    nameH = nameH.trim();
    const tempNeed: Need = {
      id: 1,
      name: nameH,
      price: Number(price),
      quantity: Number(quantity),
    };
    if (!nameH) { return; }

    this.needService.addNeed(tempNeed)
      .subscribe(need => {
        this.needs.push(tempNeed);
      });
  }

  delete(need: Need): void {
    this.needs = this.needs.filter(n => n !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

}


