import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AccountItem } from "../account/account-datasource";

@Component({
  selector: 'account-dialog',
  templateUrl: 'account-dialog.html'
})
export class AccountDialog {
  constructor(
    public dialogRef: MatDialogRef<AccountDialog>,
    @Inject(MAT_DIALOG_DATA) public data: AccountItem,
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
}
