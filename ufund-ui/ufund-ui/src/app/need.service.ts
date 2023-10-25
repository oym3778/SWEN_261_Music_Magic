import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs';

//Makes this class an injectable dependecy which can be injected into any class
//in the program. 
@Injectable({
  providedIn: 'root'
})
//Service which gets the needs from our backend server. 
export class NeedService {

  private needsUrl = "http://localhost:8080/needs" //url of REST tomcat server

  constructor(
    private http: HttpClient) { }

  //Print any encountered errors to the console
  private handleError<T>(operation = 'operation', result?: T)
  {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T); 
    }
  }

  //return an observable containing the needs acquired from the server at needsUrl
  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl)
      .pipe(
        catchError(this.handleError<Need[]>('getHeroes', []))
      );
  }
}
