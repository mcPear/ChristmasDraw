import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DenyRequestConfirmationComponent } from './deny-request-confirmation.component';

describe('DenyRequestConfirmationComponent', () => {
  let component: DenyRequestConfirmationComponent;
  let fixture: ComponentFixture<DenyRequestConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DenyRequestConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DenyRequestConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
