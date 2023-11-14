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

  constructor(private location: Location) { }

  //Go to login page
  goBack(): void {
    this.location.back();
  }
}
