import {Component, Inject} from '@angular/core';
import {UserEditComponent} from "../user-edit/user-edit.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent {

  groupName: string;
  groups: string[];

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: string[]) {
    this.groups = data;
    this.groupName = "mock";
  }

  groupNameAvailable(name: string): boolean {
    return this.groups.indexOf(name) == -1;
  }

}
