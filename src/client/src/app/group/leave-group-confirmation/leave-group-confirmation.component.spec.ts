import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveGroupConfirmationComponent } from './leave-group-confirmation.component';

describe('LeaveGroupConfirmationComponent', () => {
  let component: LeaveGroupConfirmationComponent;
  let fixture: ComponentFixture<LeaveGroupConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaveGroupConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveGroupConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
