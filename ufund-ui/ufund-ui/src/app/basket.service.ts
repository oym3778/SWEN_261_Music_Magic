import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable, Subject, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Operation } from './needs/needs.component';

//Makes this class an injectable dependecy which can be injected into any class
//in the program. 
@Injectable({
  providedIn: 'root'
})
//Service which gets the needs from our backend server. 
export class BasketService {

  private needsUrl = "http://localhost:8080/basket" //url of REST tomcat server
  private basketMessanger = new Subject(); //used to send data to funding-basket.component.ts from other components

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }


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

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getUpdate(): Observable<any> {
    return this.basketMessanger.asObservable(); 
  }

  addBasketSubjects(need: Need): void {
    this.basketMessanger.next({operation: Operation.ADD, need: need});
  }

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
  addNeedToBasket(need: Need): Observable<Need> {
    return this.http.post<Need>(`${this.needsUrl}/add`, need, this.httpOptions).pipe(
      // tap((newHero: Need) => this.log(`added hero w/ id=${newHero.id}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  deleteNeed(need: Need) : Observable<Need> {
    this.basketMessanger.next({operation: Operation.DELETE, need: need});
    return this.deleteNeedServer(need.id);
  }

  /** DELETE: delete a need from the funding basket */
  private deleteNeedServer(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      // tap(_ => this.log(`deleted hero id=${id}`)),
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }










}
