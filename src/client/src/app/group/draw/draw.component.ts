import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupDto} from "../../shared/dto/group.dto";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroup} from "../../shared/model/selected-group-data";
import {GroupSimpleDto} from "../../shared/dto/group_simple";

@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  styleUrls: ['./draw.component.css']
})
export class DrawComponent implements OnInit {
  @Input() selectedGroup: SelectedGroup;
  @Output() drawPerformed = new EventEmitter<string>();
  group: GroupSimpleDto;
  defaultV: string;
  selectors = [
    "yes",
    "no"
  ];

  constructor(private service: UserService) {
  }

  async ngOnInit() {
    this.group = await this.service.getGroup(this.selectedGroup.name);
    if(this.group.countChildren == true)
      this.defaultV = 'yes';
    else if(this.group.countChildren == false)
      this.defaultV = 'no';
    console.log(this.defaultV);
  }

  async performDraw() {
    console.log(this.group);
    await this.service.performDraw(this.selectedGroup.name);
    this.drawPerformed.emit();
    this.ngOnInit();
  }


  getIsDrawnOutput() {
    if (this.group.isDrawn) return 'yes';
    else return 'no';
  }

  async saveData(){
    await this.service.updateGroup(this.group);
  }

  setCurrentData(event){
    this.group.drawDate = event.target.valueAsNumber;
  }

  setIncludeChildren(event){
    if(event.target.value == 'yes')
      this.group.countChildren = true;
    else if(event.target.value == 'no')
      this.group.countChildren = false;
  }
}
