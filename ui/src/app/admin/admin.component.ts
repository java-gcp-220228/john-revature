import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';

export interface Client {
  id: number;
  firstName: string;
  lastName: string;
  age: number;
  accounts?: Account[];
}

export interface Account {
  id: number;
  type: string;
  balance: number;
  clientId: number;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  clients!: Client[];

  constructor(private api: ApiService) { }

  loadAccounts(client: Client): void{
    this.api.getAccounts('' + client.id).subscribe(
      (a) => client.accounts = a
    );
  }

  ngOnInit(): void {
    this.api.getClients().subscribe(
      (c) => this.clients = c
    );
  }

}
