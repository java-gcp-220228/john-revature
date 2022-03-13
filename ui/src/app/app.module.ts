import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClientComponent } from './client/client.component';
import { HttpClientModule } from '@angular/common/http'
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list'
import { MatIconModule } from '@angular/material/icon'
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { ClientDetailsComponent } from './client-details/client-details.component';
import { AccountComponent } from './account/account.component';
import { ApiService } from './api.service';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { AdminComponent } from './admin/admin.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ClientDialog } from './dialog/client.dialog';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ClientComponent,
    ClientDetailsComponent,
    AccountComponent,
    AccountDetailsComponent,
    AdminComponent,
    ClientDialog
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatListModule,
    MatIconModule,
    MatTableModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
