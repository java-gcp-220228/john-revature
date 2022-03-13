import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountItem } from '../account/account-datasource';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  account!: AccountItem;

  constructor(private route: ActivatedRoute,
    private api: ApiService) {}

  ngOnInit(): void {
    let clientId = this.route.snapshot.paramMap.get('clientId')!;
    let accountId = this.route.snapshot.paramMap.get('accountId')!;
    this.api.getAccountById(clientId,accountId).subscribe(
      (account) => this.account = account
    );
    if (this.account == undefined) this.account = {id: 0, type: '', balance: 0, clientId: 0};
  }

}
