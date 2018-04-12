import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-group-container',
  templateUrl: './group-container.component.html',
  styleUrls: ['./group-container.component.css']
})
export class GroupContainerComponent implements OnInit {

  @Input() username: string;
  @Input() groupName: string;

  constructor() { }

  ngOnInit() {
  }

}
