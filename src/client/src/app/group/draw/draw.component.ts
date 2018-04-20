import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupDto} from "../../shared/dto/group.dto";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroupData} from "../../shared/model/selected-group-data";

@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  styleUrls: ['./draw.component.css']
})
export class DrawComponent implements OnInit {
  @Input() selectedGroup: SelectedGroupData;
  @Output() drawPerformed = new EventEmitter<string>();
  group: GroupDto;

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
  }

  async performDraw() {
    await this.service.performDraw(this.selectedGroup.name);
    this.drawPerformed.emit();
    this.ngOnInit();
  }

  getDrawDateOutput() {
    if (this.group.drawDate) return this.group.drawDate;
    else return 'unknown';
  }

  getIsDrawnOutput() {
    if (this.group.isDrawn) return 'yes';
    else return 'no';
  }

  getCountChildrenOutput() {
    if (this.group.countChildren) return 'yes';
    else return 'no';
  }

  getGiftValueOutput() {
    if (this.group.giftValue) return this.group.giftValue;
    else return 'unknown';
  }

  getChildGiftValueOutput() {
    if (this.group.childGiftValue) return this.group.childGiftValue;
    else return 'unknown';
  }

  getCollectorContactOutput() {
    if (this.group.collectorContact) return this.group.collectorContact;
    else return 'unknown';
  }

}
