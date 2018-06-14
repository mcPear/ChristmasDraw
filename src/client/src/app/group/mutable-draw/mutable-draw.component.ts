import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupSimpleDto} from "../../shared/dto/group_simple";
import {MatDatepickerInputEvent} from "@angular/material";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroup} from "../../shared/model/selected-group-data";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-mutable-draw',
  templateUrl: './mutable-draw.component.html',
  styleUrls: ['./mutable-draw.component.css']
})
export class MutableDrawComponent implements OnInit {
  @Input() selectedGroup: SelectedGroup;
  @Output() drawPerformed = new EventEmitter<string>();
  group: GroupSimpleDto;
  dueDateTranslation: string;
  giftValueTranslation: string;
  childGiftValueTranslation: string;
  contactTranslation: string;
  alreadyDrawnTranslation: string;
  notYetDrawnTranslation: string;
  groupNameTranslationParam: Object;

  constructor(private service: UserService, private translate: TranslateService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
    this.groupNameTranslationParam = {value: this.group.name}
    this.translate.get('DUE_DATE').subscribe((res: string) => {
      this.dueDateTranslation = res
    });
    this.translate.get('VALUE_OF_GIFT').subscribe((res: string) => {
      this.giftValueTranslation = res
    });
    this.translate.get('VALUE_OF_GIFT_FOR_CHILD').subscribe((res: string) => {
      this.childGiftValueTranslation = res
    });
    this.translate.get('CONTACT').subscribe((res: string) => {
      this.contactTranslation = res
    });
    this.translate.get('ALREADY_DRAWN').subscribe((res: string) => {
      this.alreadyDrawnTranslation = res
    });
    this.translate.get('NOT_YET_DRAWN').subscribe((res: string) => {
      this.notYetDrawnTranslation = res
    });
  }

  async performDraw() {
    await this.service.performDraw(this.selectedGroup.name).catch(err => err.stat);
    this.drawPerformed.emit();
    this.ngOnInit();
  }


  getIsDrawnOutput() {
    if (this.group.drawn) return this.alreadyDrawnTranslation;
    else return this.notYetDrawnTranslation;
  }

  async saveData() {
    await this.service.updateGroup(this.group);
  }

  setCurrentData(event: MatDatepickerInputEvent<Date>) {
    this.group.drawDate = event.value.getTime();
  }

}
