import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserManegerComponent } from './user-maneger.component';

describe('UserManegerComponent', () => {
  let component: UserManegerComponent;
  let fixture: ComponentFixture<UserManegerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserManegerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManegerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
