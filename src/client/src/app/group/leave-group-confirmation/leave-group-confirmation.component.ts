import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserEditComponent} from "../../user-edit/user-edit.component";
import {MemberGroupDto} from "../../shared/dto/member-group.dto";

@Component({
  selector: 'app-leave-group-confirmation',
  templateUrl: './leave-group-confirmation.component.html',
  styleUrls: ['./leave-group-confirmation.component.css']
})
export class LeaveGroupConfirmationComponent {

  groupDto: MemberGroupDto;

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: MemberGroupDto) {
    this.groupDto = data;
  }


}
