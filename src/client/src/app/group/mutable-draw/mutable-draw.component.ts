import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupSimpleDto} from "../../shared/dto/group_simple";
import {MatDatepickerInputEvent} from "@angular/material";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroup} from "../../shared/model/selected-group-data";

@Component({
  selector: 'app-mutable-draw',
  templateUrl: './mutable-draw.component.html',
  styleUrls: ['./mutable-draw.component.css']
})
export class MutableDrawComponent implements OnInit {
  @Input() selectedGroup: SelectedGroup;
  @Output() drawPerformed = new EventEmitter<string>();
  group: GroupSimpleDto;

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
  }

  async performDraw() {
    console.log(this.group);
    await this.service.performDraw(this.selectedGroup.name);
    this.drawPerformed.emit();
    this.ngOnInit();
  }


  getIsDrawnOutput() {
    if (this.group.isDrawn) return 'Already drawn';
    else return 'Not yet drawn';
  }

  async saveData(){
    await this.service.updateGroup(this.group);
  }

  setCurrentData(event: MatDatepickerInputEvent<Date>){
    this.group.drawDate = event.value.getTime();
  }
}
