import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiService } from '../api.service';
import { AccountDialog } from '../dialog/account.dialog';
import { ClientDialog } from '../dialog/client.dialog';
import { ConfirmDialog } from '../dialog/confirm.dialog';

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
  loading: boolean = true;
  items = Array(1000).fill(0).map(() => Math.round(Math.random() * 100));

  constructor(private api: ApiService,
    public dialog: MatDialog,
    private snackbar: MatSnackBar) {}


  newClient(): void {
    const dialogRef = this.dialog.open(ClientDialog, {
      data: {firstName: 'firstName', lastName: 'lastName', age: 0}
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.postClient(res.firstName, res.lastName, res.age).subscribe(
        (c) => {
          this.clients.push({id: c.id, firstName: c.firstName, lastName: c.lastName, age: c.age});
        }
      );
    });
  }

  updateClient(client: Client): void {
    const dialogRef = this.dialog.open(ClientDialog, {
      data: client
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.updateClient(res).subscribe();
    });
  }

  deleteClient(client: Client): void {
    if (client.accounts?.length) {
      this.snackbar.open('Cannot delete clients with open accounts');
      return;
    }
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {title: `Delete account ${client.id}`, message: 'Confirm delete?'}
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.api.deleteClient(client).subscribe();
        this.clients = this.clients.filter(c => c.id != client.id);
      }
    });

  }

  newAccount(client: Client, index: number): void {
    const dialogRef = this.dialog.open(AccountDialog, {
      data: {type: 'CHEQUING', balance: 0, clientId: client.id}
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.postAccount(client.id, res.type).subscribe(
        (a) => {
          this.clients[index].accounts?.push({id: a.id, type: a.type, balance: a.balance, clientId: a.clientId});
        });
    });
  }

  updateAccount(account: Account): void {
    const dialogRef = this.dialog.open(AccountDialog, {
      data: account
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.updateAccount(res).subscribe();
    });
  }

  deleteAccount(account: Account, index: number): void {
    if (account.balance > 0) {
      this.snackbar.open('Cannot delete account with balance > 0')
      return;
    }
    const dialogRef = this.dialog.open(ConfirmDialog, {
      data: {title: `Delete account ${account.id}`, message: 'Confirm delete?'}
    });

    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.api.deleteAccount(account).subscribe();
        this.clients[index].accounts = this.clients[index].accounts?.filter((a) => a.id != account.id);
      }
    });

  }

  loadClientAccounts(): void {
    this.loading = true;
    this.api.getClientAccounts().subscribe(
      c => {
      this.clients = c;
      this.loading = false;
      }
    )
  }

  ngOnInit(): void {
    this.loadClientAccounts();
  }

}
