import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupsOwnerComponent } from './groups-owner.component';

describe('GroupsOwnerComponent', () => {
  let component: GroupsOwnerComponent;
  let fixture: ComponentFixture<GroupsOwnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupsOwnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupsOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
