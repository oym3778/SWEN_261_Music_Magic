import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NeedDetailComponent } from './need-detail/need-detail.component'
import { NeedsComponent } from './needs/needs.component'
import { LoginComponent } from './login/login.component';
import { HelperViewComponent } from './helper-view/helper-view.component';
import { AdminViewComponent } from './admin-view/admin-view.component';
import { CheckoutComponent } from './checkout/checkout.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'detail/:id', component: NeedDetailComponent },
  { path: 'needs', component: NeedsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'helper', component: HelperViewComponent },
  { path: 'admin', component: AdminViewComponent},
  { path: 'checkout', component: CheckoutComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]

})
export class AppRoutingModule { }
