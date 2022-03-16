import { environment } from 'src/environments/environment';

import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ClientItem } from './client/client-datasource';
import { AccountItem } from './account/account-datasource';
import { Client } from './admin/admin.component';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getClients(): Observable<ClientItem[]> {
    return this.http.get<ClientItem[]>(`${environment.apiUrl}/clients`)
      .pipe(catchError(this.handleError));
  }

  getClientById(id: string): Observable<ClientItem> {
    return this.http.get<ClientItem>(`${environment.apiUrl}/clients/${id}`)
      .pipe(catchError(this.handleError));
  }

  getAccounts(id: string): Observable<AccountItem[]> {
    return this.http.get<AccountItem[]>(`${environment.apiUrl}/clients/${id}/accounts`)
      .pipe(catchError(this.handleError));
  }

  getAccountsWithParams(id: string, params: HttpParams): Observable<AccountItem[]> {
    return this.http.get<AccountItem[]>(`${environment.apiUrl}/clients/${id}/accounts`, { params: params })
      .pipe(catchError(this.handleError));
  }

  getAccountById(clientId: string, id: string): Observable<AccountItem> {
    return this.http.get<AccountItem>(`${environment.apiUrl}/clients/${clientId}/accounts/${id}`)
      .pipe(catchError(this.handleError));
  }

  postClient(firstName: string, lastName: string, age: number): Observable<ClientItem> {
    let client: ClientItem = { id: 0, firstName: firstName, lastName: lastName, age: age };
    return this.http.post<ClientItem>(`${environment.apiUrl}/clients`, client)
      .pipe(catchError(this.handleError));
  }

  updateClient(client: ClientItem): Observable<ClientItem> {
    return this.http.put<ClientItem>(`${environment.apiUrl}/clients/${client.id}`, client)
      .pipe(catchError(this.handleError));
  }

  deleteClient(client: ClientItem): Observable<ClientItem> {
    return this.http.delete<ClientItem>(`${environment.apiUrl}/clients/${client.id}`)
      .pipe(catchError(this.handleError));
  }

  getClientAccounts(): Observable<Client[]> {
    return this.http.get<Client[]>(`${environment.apiUrl}/clients-accounts`)
      .pipe(catchError(this.handleError));
  }

  postAccount(clientId: number, type: string): Observable<AccountItem> {
    let account: AccountItem = { id: 0, type: type, balance: 0, clientId: clientId }
    return this.http.post<AccountItem>(`${environment.apiUrl}/clients/${clientId}/accounts`, account)
      .pipe(catchError(this.handleError));
  }

  updateAccount(account: AccountItem): Observable<AccountItem> {
    return this.http.put<AccountItem>(`${environment.apiUrl}/clients/${account.clientId}/accounts/${account.id}`, account)
      .pipe(catchError(this.handleError));
  }

  deleteAccount(account: AccountItem): Observable<AccountItem> {
    return this.http.delete<AccountItem>(`${environment.apiUrl}/clients/${account.clientId}/accounts/${account.id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` + `body was: ${error.error}`
      );
    }
    // return an observable with a user-facing error message
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
