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
  childrenOutput: string;

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.service.getUser(this.username)
      .then(res => {
        this.user = res;
        console.log('@@@@' + res);
        this.childrenOutput = this.getChildrenOutput(res.children);
      })
      .catch(res => console.log(res));
  }

  openEditDialog(): void {
    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '600px',
      width: '700px',
      data: this.user
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.service.editUser(result)
        .then(res => this.ngOnInit())
        .catch(err => console.log(err));
    });
  }

  getChildrenOutput(count: number):string{
    if(count==0) return 'no children';
    else if(count==1) return '1 child';
    else return count+'children';
  }

}
