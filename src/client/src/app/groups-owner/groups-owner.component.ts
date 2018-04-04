import {Component, Inject, Input, OnInit} from '@angular/core';
import {UserService} from "../shared/service/user.service";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {MatDialog} from "@angular/material";
import {GroupCreateComponent} from "../group-create/group-create.component";

@Component({
  selector: 'app-groups-owner',
  templateUrl: './groups-owner.component.html',
  styleUrls: ['./groups-owner.component.css']
})
export class GroupsOwnerComponent implements OnInit {

  @Input() groupsWhereOwner: string[];

  constructor(private service: UserService, public dialog: MatDialog) { }

  async ngOnInit() {
  }

  async refresh(){
    this.groupsWhereOwner = await this.service.getOwnerGroups();
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(GroupCreateComponent, {
      height: '350px',
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if(result) {
        this.service.createGroup(result)
          .then(res => this.refresh())
          .catch(err => console.log(err));
      }
    });
  }

}
