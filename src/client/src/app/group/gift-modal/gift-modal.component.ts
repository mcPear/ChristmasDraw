import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";
import {GiftPartDto} from "../../shared/dto/gift-part.dto";
import {UserService} from "../../shared/service/user.service";

@Component({
  selector: 'app-gift-modal',
  templateUrl: './gift-modal.component.html',
  styleUrls: ['./gift-modal.component.css']
})
export class GiftModalComponent implements OnInit {

  giftParts: GiftPartDto[];
  nameInput: string;
  valueInput: number;

  constructor(@Inject(MAT_DIALOG_DATA) private groupName: string, private service: UserService) {
  }

  async ngOnInit() {
    if (!this.giftParts) {
      this.giftParts = await this.service.getAllGiftParts(this.groupName);
    }
  }

  add() {
    if (this.nameInput && this.valueInput && this.nameInput != '' && this.valueInput > 0) {
      this.giftParts.push({id: undefined, name: this.nameInput, value: this.valueInput});
      this.ngOnInit();
    }
  }

  delete(item) {
    const index = this.giftParts.indexOf(item, 0);
    if (index > -1) {
      this.giftParts.splice(index, 1);
    }
    this.ngOnInit();
  }

  sum() {
    let sum = 0;
    for (let i = 0; i < this.giftParts.length; i++) {
      sum += this.giftParts[i].value;
    }
    return sum;
  }

}
