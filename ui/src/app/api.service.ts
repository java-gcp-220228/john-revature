import { environment } from 'src/environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientItem } from './client/client-datasource';
import { AccountItem } from './account/account-datasource';

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
}
