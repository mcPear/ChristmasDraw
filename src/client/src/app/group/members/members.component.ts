import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../shared/service/user.service";
import {UserIncludeDto} from "../../shared/dto/user_include.dto";
import {MatDialog} from "@angular/material";
import {UserEditComponent} from "../../user-edit/user-edit.component";
import {UserDto} from "../../shared/dto/user.dto";
import {UserDrawComponent} from "../user-draw/user-draw.component";
import {DrawUserModalDto} from "../../shared/dto/draw_user_modal.dto";
import {UserDrawModalComponent} from "../user-draw-modal/user-draw-modal.component";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {
  @Input() groupName: string;
  members: UserIncludeDto[];

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.members = await this.service.getGroupMembers(this.groupName);
  }

  async updateIncludeMembers() {
    this.service.updateIncludeMembers(this.members, this.groupName);
  }

  openAddMemberModal(): void {
    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '600px',
      width: '700px',
      data: {
        id: undefined,
        preferredUsername: undefined,
        firstName: '',
        lastName: '',
        about: '',
        children: 0
      } as UserDto
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.addMember(this.groupName, result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
    });
  }

  openEditUserModal(member: UserIncludeDto) {
    let user: UserDto = {
      id: member.id,
      preferredUsername: member.preferredUsername,
      firstName: member.firstName,
      lastName: member.lastName,
      about: member.about,
      children: member.children
    } as UserDto;

    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '600px',
      width: '700px',
      data: user
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.updateUser(result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
    });
  }

  openDrawnUserModal(member: UserIncludeDto) {
    this.dialog.open(UserDrawModalComponent, {
      data: {groupName: this.groupName, username: member.preferredUsername} as DrawUserModalDto
    });
  }

}
