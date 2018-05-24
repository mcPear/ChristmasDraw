import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../shared/service/user.service";
import {UserIncludeDto} from "../../shared/dto/user_include.dto";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {
  @Input() groupName: string;
  members: UserIncludeDto[];

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.members = await this.service.getGroupMembers(this.groupName);
  }

  async updateIncludeMembers() {
    this.service.updateIncludeMembers(this.members, this.groupName);
  }

}
