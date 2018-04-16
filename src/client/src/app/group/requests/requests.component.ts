import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";
import {GroupCreateComponent} from "../../group-create/group-create.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {
  @Input() groupName: string;
  requests: UserDto[];

  @Output()
  requestAccepted = new EventEmitter<string>();

  constructor(private service: UserService, public dialog: MatDialog) { }

  async ngOnInit() {
    this.requests = await this.service.getRequests(this.groupName);
  }

  async acceptRequest(username: string){
    await this.service.acceptRequest(username, this.groupName);
    this.requestAccepted.emit();
    this.ngOnInit();
  }

  openDenyRequestConfirmationPopup(username: string): void {
    // let dialogRef = this.dialog.open(DenyRequestConfirmationDialog, {
    //   height: '350px',
    //   width: '350px'
    // });
    //
    // dialogRef.afterClosed().subscribe(result => {
    //   if(result) {
    //     this.denyRequest(username);
    //   }
    // });
  }

  async denyRequest(username: string){
    //await this.service.denyRequest(username, this.groupName);
    //this.ngOnInit();
  }

}
