import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {
  @Input() groupName: string;
  members: UserDto[];

  constructor(private service: UserService) { }

  async ngOnInit() {
    this.members = await this.service.getMembers(this.groupName);
  }

  saveIncludeMemberList(){

  }

}
