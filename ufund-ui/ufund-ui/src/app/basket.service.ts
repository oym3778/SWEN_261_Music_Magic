import { Injectable } from '@angular/core';
import { Need } from './need';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Operation } from './needs/needs.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';

//Makes this class an injectable dependecy which can be injected into any class
//in the program. 
@Injectable({
  providedIn: 'root'
})
//Service which gets the needs from our backend server. 
export class BasketService {

  private needsUrl = "http://localhost:8080/basket" //url of REST tomcat server
  private basketMessanger = new BehaviorSubject<Need[]>([]); //used to send data to funding-basket.component.ts from other components

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {
      //make sure our BehaviorSubject starts with the correct list of needs.
      this.getNeeds().subscribe(needs => this.basketMessanger = new BehaviorSubject<Need[]>(needs))
     }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

  getUpdate(): Observable<any> {
    return this.basketMessanger.asObservable(); 
  }

  //Methods for interacting with the basket component from other components.

  /**
   * Add a need to the funding basket
   * @param need - The need to be added
   */
  addNeed(need: Need): void {
    const needs = this.basketMessanger.value;
    this.basketMessanger.next([... needs.filter(n => n != need), need]);

    this.addNeedServer(need).subscribe(); 
  }

  /**
   * Remove a need from the funding basket
   * @param need - the need to be removed.
   */
  deleteNeed(need: Need) : void {
    const needs = this.basketMessanger.value
    this.basketMessanger.next(needs.filter(n => n != need));

    this.deleteNeedServer(need.id).subscribe();
  }



  //Methods which interact directly with the backend server. 

  /** GET needs from the server */
  getNeeds(): Observable<Need[]> {
    this.messageService.add('HeroService: fetched heroes'); //For search -Daniel Tsouri
    return this.http.get<Need[]>(this.needsUrl)
      .pipe(
        // tap(_ => this.log('fetched heroes')),
        catchError(this.handleError<Need[]>('getNeeds', []))
      );
  }

  /** POST: add a new hero to the server */
  addNeedServer(need: Need): Observable<Need> {
    return this.http.post<Need>(`${this.needsUrl}/add`, need, this.httpOptions).pipe(
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  /** DELETE: delete a need from the funding basket */
  deleteNeedServer(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }










}
