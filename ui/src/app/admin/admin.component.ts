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
  client: Client = {id: 0, firstName: 'First', lastName: 'Last', age: 0};

  constructor(private api: ApiService,
    public dialog: MatDialog,
    private snackbar: MatSnackBar) {}


  newClient(): void {
    const dialogRef = this.dialog.open(ClientDialog, {
      data: {firstName: this.client.firstName, lastName: this.client.lastName, age: this.client.age}
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.postClient(res.firstName, res.lastName, res.age).subscribe(
        () => this.loadClientAccounts()
      );
    });
  }

  updateClient(client: Client): void {
    const dialogRef = this.dialog.open(ClientDialog, {
      data: client
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.updateClient(res).subscribe(
        () => this.loadClientAccounts()
      );
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
        this.loadClientAccounts();
      }
    });
  }

  newAccount(client: Client): void {
    const dialogRef = this.dialog.open(AccountDialog, {
      data: {type: 'CHEQUING', balance: 0, clientId: client.id}
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.postAccount(client.id, res.type).subscribe(
        () => this.loadClientAccounts()
      );
    });
  }

  updateAccount(account: Account): void {
    const dialogRef = this.dialog.open(AccountDialog, {
      data: account
    });

    dialogRef.afterClosed().subscribe(res => {
      this.api.updateAccount(res).subscribe(
        () => this.loadClientAccounts()
      );
    });
  }

  deleteAccount(account: Account): void {
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
        this.loadClientAccounts();
      }
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
