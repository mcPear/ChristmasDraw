import {Component, Inject, Input} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {UserService} from "../shared/service/user.service";
import {GroupsDto} from "../shared/dto/groups.dto";

@Component({
  selector: 'app-group-join',
  templateUrl: './group-join.component.html',
  styleUrls: ['./group-join.component.css']
})
export class GroupJoinComponent {

  groupName: string;
  groupExists = false;
  groupsWhereOwner: string[];
  groupsWhereMember: string[];

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: GroupsDto,
              private service: UserService) {
    this.groupName = "example";
    this.groupsWhereOwner = data.groupsWhereOwner;
    this.groupsWhereMember = data.groupsWhereMember;
  }


  async checkIfGroupExists(name: string) {
    this.groupExists =  await this.service.existsGroup(name);
  }

  isOwnedGroup(name: string): boolean{
    return this.groupsWhereOwner.indexOf(name) != -1;
  }

  isJoinedGroup(name: string): boolean{
    return this.groupsWhereMember.indexOf(name) != -1;
  }

  onKey(event: any) {
    this.checkIfGroupExists(event.target.value);
  }

}
