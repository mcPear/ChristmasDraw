import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../shared/service/user.service";
import {UserEditComponent} from "../user-edit/user-edit.component";
import {MatDialog} from "@angular/material";
import {GroupCreateComponent} from "../group-create/group-create.component";
import {AppCacheStorage} from "../shared/storage/app-cache-storage";

@Component({
  selector: 'app-groups-owner',
  templateUrl: './groups-owner.component.html',
  styleUrls: ['./groups-owner.component.css']
})
export class GroupsOwnerComponent implements OnInit {

  @Input() groupsWhereOwner: string[]; //FIXME consider if needed
  @Output() groupSelected = new EventEmitter<string>();

  constructor(public dialog: MatDialog, private cacheStorage: AppCacheStorage) {
  }

  async ngOnInit() {
  }

  async refresh() {
    this.groupsWhereOwner = await this.cacheStorage.getOwnedGroups()
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(GroupCreateComponent, {
      height: '350px',
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result) {
        this.cacheStorage.addOwnedGroup(result)
          .then(res => this.refresh())
          .catch(err => console.log(err));
      }
    });
  }

  selectGroup(name: string) {
    this.groupSelected.emit(name);
  }

}
