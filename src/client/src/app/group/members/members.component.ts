import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../shared/service/user.service";
import {UserIncludeDto} from "../../shared/dto/user_include.dto";
import {MatDialog} from "@angular/material";
import {UserEditComponent} from "../../user-edit/user-edit.component";
import {UserDto} from "../../shared/dto/user.dto";

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

}
