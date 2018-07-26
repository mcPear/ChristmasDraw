import {Component} from '@angular/core';
import {UserService} from "../shared/service/user.service";

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent {

  groupName: string;
  groupExists = true;
  groupNameTranslation: string;

  constructor(private service: UserService) {
    this.groupName = "example";
  }

  async checkIfGroupExists(name: string) {
    this.groupExists = await this.service.existsGroup(name);
  }

  onKey(event: any) {
    this.checkIfGroupExists(event.target.value);
  }

}
