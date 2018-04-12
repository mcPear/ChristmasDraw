import {Component, Input, OnInit} from '@angular/core';
import {SelectedGroupData} from "../../shared/model/selected-group-data";

@Component({
  selector: 'app-group-container',
  templateUrl: './group-container.component.html',
  styleUrls: ['./group-container.component.css']
})
export class GroupContainerComponent implements OnInit {

  @Input() username: string;
  @Input() selectedGroup: SelectedGroupData;

  constructor() { }

  ngOnInit() {
  }

}
