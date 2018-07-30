import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";

@Component({
  selector: 'app-delete-group-confirmation',
  templateUrl: './delete-group-confirmation.component.html',
  styleUrls: ['./delete-group-confirmation.component.css']
})
export class DeleteGroupConfirmationComponent {

  groupParam: Object;

  constructor(@Inject(MAT_DIALOG_DATA) public data: string) {
    this.groupParam = {value: this.data}
  }


}
