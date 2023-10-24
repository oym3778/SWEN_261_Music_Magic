import { Component } from '@angular/core';
import { Need } from '../need'
import { NEEDS } from '../mock-needs';
import { NeedService } from '../need.service';

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


