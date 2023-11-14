import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  messages: string[] = [];
  private subjectName = new Subject<any>(); 

  add(message: string) {
    this.messages.push(message);
    this.subjectName.next({String : message});
  }

  getUpdate(): Observable<any> {
    return this.subjectName.asObservable();
  }

  clear() {
    this.messages = [];
  }
}