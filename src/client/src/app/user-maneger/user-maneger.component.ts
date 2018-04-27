import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../shared/dto/user.dto";
import {MatDialog} from "@angular/material";
import {UserService} from "../shared/service/user.service";

@Component({
  selector: 'app-user-maneger',
  templateUrl: './user-maneger.component.html',
  styleUrls: ['./user-maneger.component.css']
})
export class UserManegerComponent implements OnInit {
  @Input() username: string;
  users: UserDto[];
  user: UserDto = null;
  constructor(private service: UserService, public dialog: MatDialog) { }

  async ngOnInit() {
    this.users = await this.service.getAllUsers();
    this.user = await this.service.getUser(this.username);
  }

  async deleteUser(user){
    await this.service.deleteUser(user.preferredUsername);
    this.users = this.users.filter(item => item !== user);
  }

}
