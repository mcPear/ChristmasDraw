import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "./user.service";
import {UserDto} from "./user.dto";
import {MatDialog} from "@angular/material";
import {UserEditComponent} from "../user-edit/user-edit.component";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input() username: string;
  user: UserDto = null;

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.service.getUser(this.username)
      .then(res => {
        this.user = res;
        console.log('@@@@' + res);
      })
      .catch(res => console.log(res));
  }

  openEditDialog(): void {
    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '400px',
      width: '600px',
      data: this.user
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.service.editUser(result)
        .catch(err => console.log(err));
    });
  }

}
