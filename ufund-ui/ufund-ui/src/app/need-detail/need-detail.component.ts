import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Need } from '../need';
import { NeedService } from '../need.service';



@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  @Input() need?: Need; 

  constructor(private needService: NeedService) {}

  saveNeed(): void {
    if(this.need){
      this.needService.updateNeed(this.need).subscribe();
    }
  }
}
