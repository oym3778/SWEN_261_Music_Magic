import { Component } from '@angular/core';
import { NeedService } from '../need.service';
import { Need } from '../need';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-add-need',
  templateUrl: './add-need.component.html',
  styleUrls: ['./add-need.component.css']
})
export class AddNeedComponent {
  constructor(private needService: NeedService) { }

  add(nameH: string, price: string, quantity: string): void {
    nameH = nameH.trim();
    const tempNeed: Need = {
      id: 0,
      name: nameH,
      price: Number(price),
      quantity: Number(quantity),
    };
    //if (!nameH) { return; }

    this.needService.addNeed(tempNeed);
  }
}
