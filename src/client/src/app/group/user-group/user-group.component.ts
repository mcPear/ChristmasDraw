import {Component, Input, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material";
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-user-group',
  templateUrl: './user-group.component.html',
  styleUrls: ['./user-group.component.css']
})
export class UserGroupComponent implements OnInit {

  @Input() username: string;
  user: UserDto = null;
  childrenOutput: string;

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.user = await this.service.getUser(this.username);
    this.childrenOutput = this.getChildrenOutput(this.user.children);
  }

  getChildrenOutput(count: number): string {
    if (count == 0) return 'no children';
    else if (count == 1) return '1 child';
    else return count + ' children';
  }

}
