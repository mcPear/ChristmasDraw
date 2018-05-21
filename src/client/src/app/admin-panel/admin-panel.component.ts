import {Component, EventEmitter, Input, OnInit} from '@angular/core';
import {UserDto} from "../shared/dto/user.dto";
import {UserService} from "../shared/service/user.service";
import {MatDialog} from "@angular/material";
import {GroupSimpleDto} from "../shared/dto/group_simple";
import {isNullOrUndefined} from "util";
import {DatePipe} from "@angular/common";
import {GroupCreateComponent} from "../group-create/group-create.component";
import {SelectedGroup} from "../shared/model/selected-group-data";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  @Input() username: string;
  user: UserDto = null;
  groups: GroupSimpleDto[] = null;
  groupSelected : SelectedGroup;

  constructor(private service: UserService, public dialog: MatDialog) { }

  async ngOnInit() {
    this.user = await this.service.getUser(this.username);
    this.groups = await this.service.getAllGroups();
    console.log("xD");
  }

  generateData(timespan){
    if(!isNullOrUndefined(timespan)){
      return '';
    }
    return '';
  }

  async deleteGroup(group) {
    await this.service.deleteGroup(this.username, group.name);
    this.groups = this.groups.filter(item => item !== group);
  }

  showDetails(item) : void{
    this.groupSelected = <SelectedGroup>{name: item.name, isOwned:true};
  }
}
