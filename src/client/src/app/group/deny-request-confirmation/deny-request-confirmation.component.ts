import {Component, Inject} from '@angular/core';
import {UserEditComponent} from "../../user-edit/user-edit.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserDto} from "../../shared/dto/user.dto";

@Component({
  selector: 'app-deny-request-confirmation',
  templateUrl: './deny-request-confirmation.component.html',
  styleUrls: ['./deny-request-confirmation.component.css']
})
export class DenyRequestConfirmationComponent {

  userDto: UserDto;

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: UserDto) {
    this.userDto = data;
  }

}
