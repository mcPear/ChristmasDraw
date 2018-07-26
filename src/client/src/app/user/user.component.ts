import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../shared/dto/user.dto";
import {MatDialog} from "@angular/material";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {AppCacheStorage} from "../shared/storage/app-cache-storage";
import {TranslateService} from "@ngx-translate/core";
import {Observable} from "rxjs/Observable";

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
    this.user = await this.cacheStorage.getUserDto();
  }

  openEditDialog(): void {
    let dialogRef = this.dialog.open(UserEditComponent, {
      height: '600px',
      width: '700px',
      data: this.user
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cacheStorage.updateUserDto(result)
          .then(res => this.ngOnInit())
          .catch(err => console.log(err));
      }
    });
  }

}
