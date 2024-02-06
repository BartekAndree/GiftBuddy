import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { catchError, share } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

export interface HTTPError {
  httpErrorNumber?: number;
  httpErrorMessage?: string;
  originalError?: HttpErrorResponse;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {
  }

  handleErrors(error: HttpErrorResponse): Observable<HTTPError> {
    const httpError: HTTPError = {};

    httpError.originalError = error;

    if (error.error instanceof ErrorEvent) {
      httpError.httpErrorMessage = error.error.message;
    } else {
      httpError.httpErrorNumber = error.status;
    }

    return throwError(httpError);
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(`${environment.apiUrl}${path}`, { params }).pipe(
      catchError(this.handleErrors),
      share({
        resetOnError: false,
        resetOnComplete: false,
        resetOnRefCountZero: false,
      }),
    );
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http
      .put(
        `${environment.apiUrl}${path}`,
        JSON.stringify(body),
        httpOptions,
      )
      .pipe(
        catchError(this.handleErrors),
        share({
          resetOnError: false,
          resetOnComplete: false,
          resetOnRefCountZero: false,
        }),
      );
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(
        `${environment.apiUrl}${path}`,
        JSON.stringify(body),
        httpOptions,
      )
      .pipe(
        catchError(this.handleErrors),
        share({
          resetOnError: false,
          resetOnComplete: false,
          resetOnRefCountZero: false,
        }),
      );
  }

  delete(path: string): Observable<any> {
    return this.http
      .delete(`${environment.apiUrl}${path}`, httpOptions)
      .pipe(
        catchError(this.handleErrors),
        share({
          resetOnError: false,
          resetOnComplete: false,
          resetOnRefCountZero: false,
        }),
      );
  }
}
