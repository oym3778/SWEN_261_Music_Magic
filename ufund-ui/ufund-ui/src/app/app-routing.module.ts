import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NeedDetailComponent } from './need-detail/need-detail.component'
import { NeedsComponent } from './needs/needs.component'

const routes: Routes = [
  { path: '', redirectTo: '/needs', pathMatch: 'full' },
  { path: 'detail/:id', component: NeedDetailComponent },
  { path: 'needs', component: NeedsComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]

})
export class AppRoutingModule { }
