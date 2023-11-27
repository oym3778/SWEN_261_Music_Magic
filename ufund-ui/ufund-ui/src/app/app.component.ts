import { Component } from '@angular/core';
import { SlideInterface } from './imageSlider/types/slide.interface';

/**
 * Give the name to the associated html tag for this component and connect
 * the html and style sheets */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

//Basic AppComponent class
export class AppComponent {
  
  title = 'MusicMagic'; //page title.
  
}
