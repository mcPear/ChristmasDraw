import {Component} from '@angular/core';
import {UserEditComponent} from "../user-edit/user-edit.component";
import {MatDialogRef} from "@angular/material";
import {UserService} from "../shared/service/user.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent {

  groupName: string;
  groupExists = true;
  groupNameTranslation: string;

  constructor(public dialogRef: MatDialogRef<UserEditComponent>, private service: UserService,
              private translate: TranslateService) {
    this.groupName = "example";
    this.translate.get('GROUP_NAME').subscribe((res: string) => {
      this.groupNameTranslation = res
    });
  }

  groupNameAvailable(name: string): boolean {
    return false; //FIXME service
  }

  async checkIfGroupExists(name: string) {
    this.groupExists = await this.service.existsGroup(name);
  }

  onKey(event: any) {
    this.checkIfGroupExists(event.target.value);
  }

}
