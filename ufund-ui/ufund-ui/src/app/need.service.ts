import { Injectable } from '@angular/core';
import { Need } from './need';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
//import { HEROES } from './mock-needs';
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
  private needsMessanger = new Subject(); //used to send data to needs.component.ts from other components

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

  /**
   * Return this class' subject as an observable so it can be subscribed to.
   */
  getUpdate(): Observable<any> {
    return this.needsMessanger.asObservable(); 
  }

  addNeedSubjects(need: Need): void {
    this.needsMessanger.next({operation: Operation.ADD, need: need});
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
      // tap(_ => this.log(`fetched need id=${id}`)),
      catchError(this.handleError<Need>(`getNeed id=${id}`))
    );
  }

  /* GET heroes whose name contains search term */
  //searchNeeds(term: string): Observable<Need[]> {
    //if (!term.trim()) {
      // if not search term, return empty hero array.
    //  return of([]);
   // }
   // return this.http.get<Need[]>(`${this.needsUrl}/?name=${term}`).pipe(
      // tap(x => x.length ?
      //   this.log(`found heroes matching "${term}"`) :
      //   this.log(`no heroes matching "${term}"`)),
    //  catchError(this.handleError<Need[]>('searchNeeds', []))
    //);
 // }

  //////// Save methods //////////

  /** POST: add a new hero to the server */
  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(`${this.needsUrl}/add`, need, this.httpOptions).pipe(
      // tap((newHero: Need) => this.log(`added hero w/ id=${newHero.id}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  /** DELETE: delete the hero from the server */
  deleteNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      // tap(_ => this.log(`deleted hero id=${id}`)),
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }

  /** PUT: update the need on the server */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsUrl, need, this.httpOptions).pipe(
      // tap(_ => this.log(`updated hero id=${hero.id}`)),
      catchError(this.handleError<any>('updateNeed'))
    );
  }

  // ------------------TO-DO------------------
  // Add this once you add the MessageService
  // /** Log a HeroService message with the MessageService */
   private log(message: string) {
    this.messageService.add(`HeroService: ${message}`);
   }

  /* GET heroes whose name contains search term */

searchNeeds(term: string): Observable<Need[]> {
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<Need[]>(`${this.needsUrl}/?name=${term}`).pipe(
    tap(x => x.length ?
       this.log(`found heroes matching "${term}"`) :
       this.log(`no heroes matching "${term}"`)),
    catchError(this.handleError<Need[]>('searchHeroes', []))
  );
}










}
