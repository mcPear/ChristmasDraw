import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material";
import {GroupCreateComponent} from "../group-create/group-create.component";
import {AppCacheStorage} from "../shared/storage/app-cache-storage";
import {DeleteGroupConfirmationComponent} from "../delete-group-confirmation/delete-group-confirmation.component";

@Component({
  selector: 'app-groups-owner',
  templateUrl: './groups-owner.component.html',
  styleUrls: ['./groups-owner.component.css']
})
export class GroupsOwnerComponent implements OnInit {

  groupsWhereOwner: string[];
  @Output() groupSelected = new EventEmitter<string>();

  constructor(public dialog: MatDialog, private cacheStorage: AppCacheStorage, private storage: AppCacheStorage) {
  }

  async ngOnInit() {
    this.loadGroupsWhereOwner()
  }

  async loadGroupsWhereOwner() {
    this.groupsWhereOwner = await this.cacheStorage.getOwnedGroups()
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(GroupCreateComponent, {
      height: '350px',
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cacheStorage.addOwnedGroup(result)
          .then(res => this.loadGroupsWhereOwner())
          .catch(err => console.log(err));
      }
    });
  }

  selectGroup(name: string) {
    this.groupSelected.emit(name);
  }

  openDeleteConfirmationPopup(group: string): void {
    let dialogRef = this.dialog.open(DeleteGroupConfirmationComponent, {
      height: '180px',
      width: '350px',
      data: group
    });

    dialogRef.afterClosed().subscribe(isConfirmed => {
      if (isConfirmed) {
        this.deleteGroup(group);
      }
    });
  }

  async deleteGroup(name: string) {
    await this.storage.deleteGroup(name);
    this.ngOnInit();
  }

}
