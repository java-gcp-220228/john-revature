import { HttpParams } from '@angular/common/http';
import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../api.service';
import { AccountDataSource, AccountItem } from './account-datasource';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<AccountItem>;
  @Input('client-id') id!: string;
  dataSource!: AccountDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'type', 'balance', 'clientId'];

  constructor(private api: ApiService,
    private route: ActivatedRoute,
    ) {
    if (this.id == undefined) this.route.snapshot.paramMap.get('clientId')!
  }

  ngOnInit(): void {
    let queryParamMap = this.route.snapshot.queryParamMap;
    if (queryParamMap.has('amountLessThan') || queryParamMap.has('amountGreaterThan')) {
      this.route.queryParams.subscribe(p => {
        let params: HttpParams = new HttpParams({fromObject: p})
        this.dataSource = new AccountDataSource(this.api.getAccountsWithParams(this.id, params))
      });
    } else {
      this.dataSource = new AccountDataSource(this.api.getAccounts(this.id));
    }
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
