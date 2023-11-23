import { Injectable } from '@angular/core';
import { Need } from './need';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
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
export class NeedService {

  private needsUrl = "http://localhost:8080/needs" //url of REST tomcat server
  private needsMessanger = new BehaviorSubject<Need[]>([]); //used to send data to needs.component.ts from other components
  private filterMessanger = new BehaviorSubject<String>('');

  constructor(
    private http: HttpClient) {
      this.getNeeds().subscribe(needs => this.needsMessanger = new BehaviorSubject<Need[]>(needs));
    }


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

  /**
   * Return this class' subject as an observable so it can be subscribed to.
   */
  getUpdate(): Observable<any> {
    return this.needsMessanger.asObservable(); 
  }

  getFilterMessanger(): Observable<any> {
    return this.filterMessanger.asObservable(); 
  }

  addNeed(need: Need): void {
    const needs = this.needsMessanger.value; 
    this.addNeedServer(need).subscribe(needResponse => {
      console.log(needResponse);
      if (needResponse != null) this.needsMessanger.next([... needs.filter(n => n != needResponse), needResponse]);
      });

  }

  deleteNeed(need: Need): void {
    const needs = this.needsMessanger.value; 
    this.needsMessanger.next(needs.filter(n => n.id != need.id));

    this.deleteNeedServer(need.id).subscribe();
  }

  updateNeedsFilter(filter: String) : void {
    this.filterMessanger.next(filter); 
  }

  /** GET needs from the server */
  getNeeds(): Observable<Need[]> {
       return this.http.get<Need[]>(this.needsUrl)
      .pipe(
        catchError(this.handleError<Need[]>('getNeeds', []))
      );
  }

  /** GET need by id. Return `undefined` when id not found */
  // THIS IS NOT BEING USED BUT THOUGHT IT'D BE USEFUL
  getNeedNo404<Data>(id: number): Observable<Need> {
    const url = `${this.needsUrl}/?id=${id}`;
    return this.http.get<Need[]>(url)
      .pipe(
        map(needs => needs[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          // this.log(`${outcome} need id=${id}`);
        }),
        catchError(this.handleError<Need>(`getNeed id=${id}`))
      );
  }

  /** GET need by id. Will 404 if id not found */
  getNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;
    return this.http.get<Need>(url).pipe(
      catchError(this.handleError<Need>(`getNeed id=${id}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new hero to the server */
  addNeedServer(need: Need): Observable<Need> {
    return this.http.post<Need>(`${this.needsUrl}/add`, need, this.httpOptions).pipe(
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  /** DELETE: delete the hero from the server */
  deleteNeedServer(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }

  /** PUT: update the need on the server */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsUrl, need, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateNeed'))
    );
  }

}
