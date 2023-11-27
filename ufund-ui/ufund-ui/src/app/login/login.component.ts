import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SlideInterface } from '../imageSlider/types/slide.interface';
import { UserSessionService } from '../user-session.service';
import { isFormArray } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  displayError : boolean = false; 

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userSession: UserSessionService) { }

  username: string | undefined;
  password: string | undefined;

  login() {
    if (!this.username || !this.password) {
      return;
    }
    this.userSession.setPassword(this.password); 
    this.userSession.setUser(this.username); 

    if (this.username == "admin") {
      this.userSession.getIsAdmin().subscribe(val => {
        if(val) this.router.navigate(['/admin']);
        this.displayError = true; 
      });
    } 
    else if(this.username == "helper"){ 
      this.userSession.getIsHelper().subscribe(val => {
        if(val) this.router.navigate(['/helper']);
        this.displayError=true; 
      })

    }
  }

  slides: SlideInterface[] = [
    { url: '/assets/img/the-nojo.jpeg', 
    title: 'The NOJO: New Orleans Jazz Orchestra', 
    body: 'Our organization helps fund the instruments and equipment donations to the NOJO Youth Orchestra, providing opportunities for selected high school students to learn the history, music, and styles of big band playing in an 18-piece orchestra', 
    audio: '/assets/audio/placeholder.ogg'},
    { url: '/assets/img/ben-frank-hs.webp', 
    title: 'Benjamin Franklin High School Music Department', 
    body: 'Based in New Orleans, Benjamin Franklin High School includes beginner through advanced level Orchestra and Band, along with Music Theory I and AP Music Theory classes. They need your help to fund their programs.', 
    audio: '/assets/audio/placeholder.ogg'},
    { url: '/assets/img/gnoyo.jpeg', 
    title: 'Greater New Orleans Youth Orchestra', 
    body: 'The Greater New Orleans Youth Orchestras programs includes five orchestras, a Young Artist Academy, an Endangered Instruments Program, a Chamber Music Program and Outreach programs. Combined, these programs ensure that music education and orchestral music are accessible to area youth. GNOYO also holds summer festivals, performances, and retreats for talented young musicians across the Greater New Orleans region.', 
    audio: '/assets/audio/placeholder.ogg'},
  ];
}
