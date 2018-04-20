import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";
import {MatDialog} from "@angular/material";
import {DenyRequestConfirmationComponent} from "../deny-request-confirmation/deny-request-confirmation.component";

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

  constructor(private service: UserService, public dialog: MatDialog) {
  }

  async ngOnInit() {
    this.requests = await this.service.getRequests(this.groupName);
  }

  async acceptRequest(username: string) {
    await this.service.setAcceptedMembership(username, this.groupName, true);
    this.requestAccepted.emit();
    this.ngOnInit();
  }

  async rejectRequest(username: string) {
    await this.service.setAcceptedMembership(username, this.groupName, false);
    this.ngOnInit();
  }

  openRejectConfirmationPopup(userDto: UserDto): void {
    let dialogRef = this.dialog.open(DenyRequestConfirmationComponent, {
      height: '180px',
      width: '350px',
      data: userDto
    });

    dialogRef.afterClosed().subscribe(isRejected => {
      if (isRejected) {
        this.rejectRequest(userDto.preferredUsername);
      }
    });
  }

}
