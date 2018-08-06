import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDrawModalComponent } from './user-draw-modal.component';

describe('UserDrawModalComponent', () => {
  let component: UserDrawModalComponent;
  let fixture: ComponentFixture<UserDrawModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDrawModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDrawModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
