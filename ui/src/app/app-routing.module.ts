import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { AccountComponent } from './account/account.component';
import { AdminComponent } from './admin/admin.component';
import { ClientDetailsComponent } from './client-details/client-details.component';
import { ClientResolver } from './client.resolver';
import { ClientComponent } from './client/client.component';

const routes: Routes = [
  { path: 'client', component: ClientComponent, resolve: {clients: ClientResolver} },
  { path: 'client/:clientId', component: ClientDetailsComponent },
  { path: 'client/:clientId/accounts', component: AccountComponent },
  { path: 'client/:clientId/accounts/:accountId', component: AccountDetailsComponent },
  { path: 'admin', component: AdminComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
