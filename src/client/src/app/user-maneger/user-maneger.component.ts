import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../shared/dto/user.dto";
import {MatDialog} from "@angular/material";
import {UserService} from "../shared/service/user.service";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {AppCacheStorage} from "../shared/storage/app-cache-storage";

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

  showDetails(item) : void {
    console.log(item);
    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '600px',
      width: '700px',
      data: item
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result) {
        this.cacheStorage.updateUserDto(result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
  }

}
