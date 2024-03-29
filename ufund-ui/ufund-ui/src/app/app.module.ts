import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NeedsComponent } from './needs/needs.component';
import { HttpClientModule } from '@angular/common/http';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { NeedSearchComponent } from './need-search/need-search.component';
import { MessagesComponent } from './messages/messages.component';
import { LoginComponent } from './login/login.component';
import { HelperViewComponent } from './helper-view/helper-view.component';
import { ImageSliderModule } from './imageSlider/imageSlider.module';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { AddNeedComponent } from './add-need/add-need.component';
import { AdminViewComponent } from './admin-view/admin-view.component';
import { NeedFilterPipe } from './need-filter.pipe';
import { CheckoutComponent } from './checkout/checkout.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedsComponent,
    NeedDetailComponent,
    NeedSearchComponent,
    MessagesComponent,
    LoginComponent,
    HelperViewComponent,
    FundingBasketComponent,
    AddNeedComponent,
    AdminViewComponent,
    NeedFilterPipe,
    CheckoutComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ImageSliderModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
