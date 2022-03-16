import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { ClientDataSource, ClientItem } from './client-datasource';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<ClientItem>;
  dataSource!: ClientDataSource;
  min!: number;
  max!: number;
  clients$!: Observable<ClientItem[]>;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'firstName', 'lastName', 'age'];


  constructor(
    private route: ActivatedRoute
    ) {
      this.route.queryParams.subscribe(
        p => {
          this.min = p ? p['amountGreaterThan'] : 0;
          this.max = p ? p['amountLessThan'] : Infinity;
        }
      );
    }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.clients$ = of(data['clients']);
    });
    this.dataSource = new ClientDataSource(this.clients$);
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
