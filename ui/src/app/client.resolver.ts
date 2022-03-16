import { Injectable } from '@angular/core';
import {Resolve} from '@angular/router';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ClientItem } from './client/client-datasource';

@Injectable({
  providedIn: 'root'
})
export class ClientResolver implements Resolve<ClientItem[]> {
  constructor(private api: ApiService) {}
  resolve(): Observable<ClientItem[]>|Promise<ClientItem[]>|ClientItem[] {
    return this.api.getClients();
  }
}
