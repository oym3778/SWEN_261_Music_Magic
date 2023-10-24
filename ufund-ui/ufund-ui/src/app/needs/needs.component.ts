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
export class NeedsComponent {
  needs: Need[] = [];
  constructor(private needService: NeedService) {}
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }
  ngOnInit(): void {
    this.getNeeds();
  }

}


