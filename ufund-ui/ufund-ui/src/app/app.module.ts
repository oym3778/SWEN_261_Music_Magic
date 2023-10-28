import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NeedsComponent } from './needs/needs.component';
import { HttpClientModule } from '@angular/common/http';
import { NeedDetailComponent } from './need-detail/need-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedsComponent,
    NeedDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
