import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ClientItem } from "../client/client-datasource";

@Component({
  selector: 'client-dialog',
  templateUrl: 'client-dialog.html'
})
export class ClientDialog {
  constructor(
    public dialogRef: MatDialogRef<ClientDialog>,
    @Inject(MAT_DIALOG_DATA) public data: ClientItem,
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
}
