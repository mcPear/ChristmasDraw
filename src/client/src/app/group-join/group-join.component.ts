import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserEditComponent} from "../user-edit/user-edit.component";

@Component({
  selector: 'app-group-join',
  templateUrl: './group-join.component.html',
  styleUrls: ['./group-join.component.css']
})
export class GroupJoinComponent {

  groupName: string;
  groups: string[];

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: string[]) {
    this.groups = data;
    this.groupName = "mock";
  }


  groupNameAvailable(name: string): boolean {
    return this.groups.indexOf(name) > -1;
  }

}
