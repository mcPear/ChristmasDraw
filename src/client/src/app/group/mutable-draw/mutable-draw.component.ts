import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupSimpleDto} from "../../shared/dto/group_simple";
import {MatDatepickerInputEvent} from "@angular/material";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroup} from "../../shared/model/selected-group-data";
import {UserIncludeDto} from "../../shared/dto/user_include.dto";

@Component({
  selector: 'app-mutable-draw',
  templateUrl: './mutable-draw.component.html',
  styleUrls: ['./mutable-draw.component.css']
})
export class MutableDrawComponent implements OnInit {
  @Input() selectedGroup: SelectedGroup;
  @Output() drawPerformed = new EventEmitter<string>();
  group: GroupSimpleDto;
  groupNameTranslationParam: Object;
  drawAvailable: boolean = false;

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
    this.groupNameTranslationParam = {value: this.group.name}
  }

  async performDraw() {
    await this.saveData();
    await this.service.performDraw(this.selectedGroup.name)
      .then(() => {
        this.drawPerformed.emit();
        this.ngOnInit();
      });
  }

  async saveData() {
    await this.service.updateGroup(this.group);
  }

  setCurrentData(event: MatDatepickerInputEvent<Date>) {
    this.group.drawDate = event.value.getTime();
  }

  resolveDrawButtonState(includes: UserIncludeDto[]) {
    let futureDrawIncludesCount = includes.filter(i => i.includeInFutureDraw).length;
    this.drawAvailable = futureDrawIncludesCount >= 3;
  }

}
