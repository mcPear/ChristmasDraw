import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";
import {UserDto} from "../shared/dto/user.dto";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {

  resultData: UserDto;

  constructor(@Inject(MAT_DIALOG_DATA) public data: UserDto) {
    this.resultData = data;
  }

}