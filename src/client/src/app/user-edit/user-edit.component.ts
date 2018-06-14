import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {UserDto} from "../shared/dto/user.dto";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {

  resultData: UserDto;
  firstNameTranslation: string;
  lastNameTranslation: string;
  aboutMeTranslation: string;
  childrenCountTranslation: string;

  constructor(public dialogRef: MatDialogRef<UserEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: UserDto, private translate: TranslateService) {
    this.resultData = data;
    this.translate.get('FIRST_NAME').subscribe((res: string) => {
      this.firstNameTranslation = res
    });
    this.translate.get('LAST_NAME').subscribe((res: string) => {
      this.lastNameTranslation = res
    });
    this.translate.get('ABOUT_ME').subscribe((res: string) => {
      this.aboutMeTranslation = res
    });
    this.translate.get('CHILDREN_COUNT').subscribe((res: string) => {
      this.childrenCountTranslation = res
    });
  }

}