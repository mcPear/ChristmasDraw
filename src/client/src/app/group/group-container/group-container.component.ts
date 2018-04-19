import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {SelectedGroupData} from "../../shared/model/selected-group-data";
import {MembersComponent} from "../members/members.component";
import {UserDrawComponent} from "../user-draw/user-draw.component";
import {DrawComponent} from "../draw/draw.component";

@Component({
  selector: 'app-group-container',
  templateUrl: './group-container.component.html',
  styleUrls: ['./group-container.component.css']
})
export class GroupContainerComponent implements OnInit {

  @Input() username: string;
  @Input() selectedGroup: SelectedGroupData;
  @ViewChild(MembersComponent) membersComponent: MembersComponent;
  @ViewChild(UserDrawComponent) userDrawComponent: UserDrawComponent;

  constructor() {
  }

  ngOnInit() {
  }

  refreshMembers() {
    this.membersComponent.ngOnInit();
  }

  refresh(){
    this.userDrawComponent.ngOnInit();
    this.ngOnInit();
  }

}
