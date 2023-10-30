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
  constructor(private needService: NeedService) { }
  
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

  
  add(nameH: string, price: string, quantity: string): void {
    nameH = nameH.trim();
    const tempNeed: Need = {
      id: this.genId(this.needs),
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


