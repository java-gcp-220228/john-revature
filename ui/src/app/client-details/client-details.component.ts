import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../api.service';
import { ClientItem } from '../client/client-datasource';

@Component({
  selector: 'app-client-details',
  templateUrl: './client-details.component.html',
  styleUrls: ['./client-details.component.css']
})
export class ClientDetailsComponent implements OnInit {

  client: ClientItem = {id: 0, firstName: '', lastName: '', age: 0};

  constructor(private route: ActivatedRoute,
    private api: ApiService) {}

  ngOnInit(): void {
    let clientId = this.route.snapshot.paramMap.get('clientId')!;
    this.api.getClientById(clientId).subscribe(
    (client) => this.client = client
    );
  }

}
