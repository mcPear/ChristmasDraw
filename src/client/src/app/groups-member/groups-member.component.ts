import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../shared/service/user.service";
import {MatDialog} from "@angular/material";
import {GroupJoinComponent} from "../group-join/group-join.component";
import {GroupsDto} from "../shared/dto/groups.dto";
import {MemberGroupDto} from "../shared/dto/member-group.dto";

@Component({
  selector: 'app-groups-member',
  templateUrl: './groups-member.component.html',
  styleUrls: ['./groups-member.component.css']
})
export class GroupsMemberComponent implements OnInit {

  groupsWhereMember: MemberGroupDto[];
  @Input() groupsWhereOwner: string[];
  @Output() groupSelected = new EventEmitter<string>();

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.groupsWhereMember = await this.service.getJoinedGroups();
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(GroupJoinComponent, {
      height: '350px',
      width: '350px',
      data: <GroupsDto>{
        groupsWhereOwner: this.groupsWhereOwner,
        groupsWhereMember: this.groupsWhereMember.map(g => g.groupName)
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result) {
        this.service.requestGroup(result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
    });
  }

  selectGroup(name: string) {
    this.groupSelected.emit(name);
  }

}
