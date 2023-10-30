import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-helper-view',
  templateUrl: './helper-view.component.html',
  styleUrls: ['./helper-view.component.css']
})
export class HelperViewComponent {

  constructor(private location: Location) { }

  goBack(): void {
    this.location.back();
  }
}
