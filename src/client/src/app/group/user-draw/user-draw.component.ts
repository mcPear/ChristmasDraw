import {Component, Input, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material";
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-user-draw',
  templateUrl: './user-draw.component.html',
  styleUrls: ['./user-draw.component.css']
})
export class UserDrawComponent implements OnInit {

  @Input() groupName: string;
  user: UserDto = null;
  childrenOutput: string;

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.user = await this.service.getDrawUser(this.groupName);
    if (this.user) this.childrenOutput = this.getChildrenOutput(this.user.children);
  }

  getChildrenOutput(count: number): string {
    if (count == 0) return 'no children';
    else if (count == 1) return '1 child';
    else return count + ' children';
  }

}
