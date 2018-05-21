import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupSimpleDto} from "../../shared/dto/group_simple";
import {MatDatepickerInputEvent} from "@angular/material";
import {UserService} from "../../shared/service/user.service";
import {SelectedGroup} from "../../shared/model/selected-group-data";

@Component({
  selector: 'app-immutable-draw',
  templateUrl: './immutable-draw.component.html',
  styleUrls: ['./immutable-draw.component.css']
})
export class ImmutableDrawComponent implements OnInit {
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

  getIncludeChildrenOutput() {
    if (this.group.countChildren) return 'yes';
    else return 'no';
  }

    async saveData(){
        await this.service.updateGroup(this.group);
    }

    setCurrentData(event: MatDatepickerInputEvent<Date>){
        this.group.drawDate = event.value.getTime();
    }
}
