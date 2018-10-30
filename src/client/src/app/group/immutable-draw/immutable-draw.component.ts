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
    groupNameTranslationParam: Object;

    constructor(private service: UserService) {
    }

    async ngOnInit() {
        this.group = await this.service.getGroup(this.selectedGroup.name);
        this.groupNameTranslationParam = {value: this.group.name}
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

    addEvent(){
      let date = this.group.drawDate;
      if(date){
          let start = new Date(this.group.drawDate);
          let end = new Date(this.group.drawDate);
          end.setHours( end.getHours() + 2 );
          let builder = [];
          let startISO = start.toISOString();
          startISO = startISO.slice(0,19);
          startISO = startISO.replace(/\-/g, '');
          startISO = startISO.replace(/\:/g, '');

          let endISO = end.toISOString();
          endISO = endISO.slice(0,19);
          endISO = endISO.replace(/\-/g, '');
          endISO = endISO.replace(/\:/g, '');
          builder.push(
            'https://calendar.google.com/calendar/r/eventedit?text=',
            'Draw+of+person+from+',
            this.group.name.replace(/\ /g, '+'),
            ' group',
            '&dates=',
            startISO,
            '/',
            endISO,
            '&details=Draw+for+christmas+present'
          );
          let win = window.open(builder.join(''));
          win.focus();
        }
    }
}
