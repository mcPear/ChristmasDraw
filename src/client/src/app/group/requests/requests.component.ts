import {Component, Input, OnInit} from '@angular/core';
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {
  @Input() groupName: string;
  requests: UserDto[];

  constructor(private service: UserService) { }

  async ngOnInit() {
    this.requests = await this.service.getRequests(this.groupName);
  }

  async acceptRequest(username: string){
    await this.service.acceptRequest(username, this.groupName);
    this.ngOnInit();
  }

}
