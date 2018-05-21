import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../shared/dto/user.dto";
import {MatDialog} from "@angular/material";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {AppCacheStorage} from "../shared/storage/app-cache-storage";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input() username: string;
  user: UserDto = null;
  childrenOutput: string;

  constructor(public dialog: MatDialog, private cacheStorage: AppCacheStorage) {
  }

  async ngOnInit() {
    this.user = await this.cacheStorage.getUserDto()
    this.childrenOutput = this.getChildrenOutput(this.user.children);
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
      if (result) {
        this.cacheStorage.updateUserDto(result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
    });
  }

  getChildrenOutput(count: number): string {
    if(count == null) {return 'missing information about children'}
    else if (count == 0) return 'no children';
    else if (count == 1) return '1 child';
    else return count + ' children';
  }

}
