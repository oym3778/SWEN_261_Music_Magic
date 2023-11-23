import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-need-search',
  templateUrl: './need-search.component.html',
  styleUrls: [ './need-search.component.css' ]
})
export class NeedSearchComponent implements OnInit {
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();

  constructor(private needService: NeedService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.needService.updateNeedsFilter(term); 
  }

  ngOnInit(): void {
    
  }
}