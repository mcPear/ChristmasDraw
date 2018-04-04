import {Component, Inject} from '@angular/core';
import {UserEditComponent} from "../user-edit/user-edit.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserService} from "../shared/service/user.service";

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent {

  groupName: string;
  groupExists = true;

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              private service: UserService) {
    this.groupName = "example";
  }

  groupNameAvailable(name: string): boolean {
    return false; //FIXME service
  }

  async checkIfGroupExists(name: string) {
    this.groupExists =  await this.service.groupExists(name);
  }

  onKey(event: any) {
    this.checkIfGroupExists(event.target.value);
  }

}
