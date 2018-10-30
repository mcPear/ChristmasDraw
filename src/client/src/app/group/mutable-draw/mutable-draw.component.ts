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
  includes: UserIncludeDto[] = null;

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
    this.groupNameTranslationParam = {value: this.group.name}
  }

  async performDrawActions() {
    await this.saveData();
    console.log('mutable draw below: ');
    console.log(this.includes);
    await this.performDraw();
  }

  async performDraw() {
    await this.service.performDraw(this.selectedGroup.name, this.includes)
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

  handleIncludesChange(includes: UserIncludeDto[]) {
    this.includes = includes;
    let futureDrawIncludesCount = includes.filter(i => i.includeInFutureDraw).length;
    this.drawAvailable = futureDrawIncludesCount >= 3;
  }

  async updateIncludeMembers() {
    this.service.updateIncludeMembers(this.includes, this.group.name);
  }

}
