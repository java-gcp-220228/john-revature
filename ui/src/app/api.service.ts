import { environment } from 'src/environments/environment';

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientItem } from './client/client-datasource';
import { AccountItem } from './account/account-datasource';
import { Client } from './admin/admin.component';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient) { }

  getClients(): Observable<ClientItem[]> {
    return this.http.get<ClientItem[]>(`${environment.apiUrl}/clients`);
  }

  getClientById(id: string): Observable<ClientItem> {
    return this.http.get<ClientItem>(`${environment.apiUrl}/clients/${id}`);
  }

  getAccounts(id: string): Observable<AccountItem[]> {
    return this.http.get<AccountItem[]>(`${environment.apiUrl}/clients/${id}/accounts`);
  }

  getAccountsWithParams(id: string, params: HttpParams): Observable<AccountItem[]> {
    return this.http.get<AccountItem[]>(`${environment.apiUrl}/clients/${id}/accounts`, {params: params});
  }

  getAccountById(clientId: string, id: string): Observable<AccountItem> {
    return this.http.get<AccountItem>(`${environment.apiUrl}/clients/${clientId}/accounts/${id}`);
  }

  postClient(firstName: string, lastName: string, age: number): Observable<ClientItem> {
    let client: ClientItem = {id: 0, firstName: firstName, lastName: lastName, age: age};
    return this.http.post<ClientItem>(`${environment.apiUrl}/clients`, client);
  }

  updateClient(client: ClientItem): Observable<ClientItem> {
    return this.http.put<ClientItem>(`${environment.apiUrl}/clients/${client.id}`, client);
  }

  deleteClient(client: ClientItem): Observable<ClientItem> {
    return this.http.delete<ClientItem>(`${environment.apiUrl}/clients/${client.id}`);
  }

  getClientAccounts(): Observable<Client[]> {
    return this.http.get<Client[]>(`${environment.apiUrl}/clients-accounts`);
  }

  postAccount(clientId: number, type: string): Observable<AccountItem> {
    let account: AccountItem = {id: 0, type: type, balance: 0, clientId: clientId}
    return this.http.post<AccountItem>(`${environment.apiUrl}/clients/${clientId}/accounts`, account);
  }

  updateAccount(account: AccountItem): Observable<AccountItem> {
    return this.http.put<AccountItem>(`${environment.apiUrl}/clients/${account.clientId}/accounts/${account.id}`, account);
  }

  deleteAccount(account: AccountItem): Observable<AccountItem> {
    return this.http.delete<AccountItem>(`${environment.apiUrl}/clients/${account.clientId}/accounts/${account.id}`);
  }
}
