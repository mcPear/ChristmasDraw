import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {SelectedGroup} from "../../shared/model/selected-group-data";
import {MembersComponent} from "../members/members.component";
import {UserDrawComponent} from "../user-draw/user-draw.component";
import {MutableDrawComponent} from "../mutable-draw/mutable-draw.component";
import {UserIncludeDto} from "../../shared/dto/user_include.dto";

@Component({
  selector: 'app-group-container',
  templateUrl: './group-container.component.html',
  styleUrls: ['./group-container.component.css']
})
export class GroupContainerComponent implements OnInit {

  @Input() username: string;
  @Input() selectedGroup: SelectedGroup;
  @ViewChild(MembersComponent) membersComponent: MembersComponent;
  @ViewChild(UserDrawComponent) userDrawComponent: UserDrawComponent;
  @ViewChild(MutableDrawComponent) mutableDrawComponent: MutableDrawComponent;

  constructor() {
  }

  ngOnInit() {
  }

  updateDrawButtonState(includes: UserIncludeDto[]) {
    this.mutableDrawComponent.handleIncludesChange(includes);
  }

  refreshMembers() {
    this.membersComponent.ngOnInit();
  }

  refresh() {
    this.refreshMembers();
    this.userDrawComponent.ngOnInit();
    this.ngOnInit();
  }

}
