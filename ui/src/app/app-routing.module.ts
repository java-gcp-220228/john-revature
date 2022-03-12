import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { ClientDetailsComponent } from './client-details/client-details.component';
import { ClientComponent } from './client/client.component';

const routes: Routes = [
  { path: 'clients', component: ClientComponent },
  { path: 'clients/:clientId', component: ClientDetailsComponent },
  { path: 'clients/:clientId/accounts/:accountId', component: AccountDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
