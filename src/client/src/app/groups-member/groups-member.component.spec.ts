import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupsMemberComponent } from './groups-member.component';

describe('GroupsMemberComponent', () => {
  let component: GroupsMemberComponent;
  let fixture: ComponentFixture<GroupsMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupsMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupsMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
