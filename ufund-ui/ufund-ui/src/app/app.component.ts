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
  title = 'ufund-ui'; //page title.
  slides: SlideInterface[] = [
    { url: '/assets/img/orchestra.webp', title: 'String Orchestra', body: 'Placeholder description of image 1'},
    { url: '/assets/img/symphonic-band.jpg', title: 'Symphonic Band', body: 'Placeholder description of image 2' },
    { url: '/assets/img/marching-band.jpeg', title: 'Marching Band', body: 'Placeholder description of image 3' },
  ];
}
