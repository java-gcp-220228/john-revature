import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from '../api.service';
import { ClientDialog } from '../dialog/client.dialog';

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
  client: Client = {id: 0, firstName: 'First', lastName: 'Last', age: 0};

  constructor(private api: ApiService,
    public dialog: MatDialog) {}


  newClient(): void {
    const dialogRef =this.dialog.open(ClientDialog, {
      data: {firstName: this.client.firstName, lastName: this.client.lastName, age: this.client.age}
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.postClient(res.firstName, res.lastName, res.age).subscribe(
        () => this.loadClientAccounts()
      );
    });
  }

  loadClientAccounts(): void {
    this.api.getClientAccounts().subscribe(
      c => this.clients = c
    );
  }

  ngOnInit(): void {
    this.loadClientAccounts();
  }

}
