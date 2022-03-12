import { environment } from 'src/environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientItem } from './client/client-datasource';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient) { }

  getClients(): Observable<ClientItem[]> {
    return this.http.get<ClientItem[]>(`${environment.apiUrl}/clients`);
  }
}
