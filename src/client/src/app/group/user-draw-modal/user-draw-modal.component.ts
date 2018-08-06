import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";
import {UserDto} from "../../shared/dto/user.dto";
import {UserService} from "../../shared/service/user.service";
import {DrawUserModalDto} from "../../shared/dto/draw_user_modal.dto";

@Component({
  selector: 'app-user-modal-draw',
  templateUrl: './user-draw-modal.component.html',
  styleUrls: ['./user-draw-modal.component.css']
})
export class UserDrawModalComponent implements OnInit {

  drawUserDto: DrawUserModalDto;
  user: UserDto = null;
  childrenOutput: string;

  constructor(private service: UserService, @Inject(MAT_DIALOG_DATA) public data: DrawUserModalDto) {
    this.drawUserDto = this.data;
  }

  async ngOnInit() {
    this.user = await this.service.getDrawUserOfVirtual(this.drawUserDto.groupName, this.drawUserDto.username);
    if (this.user) this.childrenOutput = this.getChildrenOutput(this.user.children);
  }

  getChildrenOutput(count: number): string {
    if (count == 0) return 'no children';
    else if (count == 1) return '1 child';
    else return count + ' children';
  }

}
