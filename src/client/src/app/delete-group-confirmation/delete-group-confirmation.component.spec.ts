import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteGroupConfirmationComponent } from './delete-group-confirmation.component';

describe('DeleteGroupConfirmationComponent', () => {
  let component: DeleteGroupConfirmationComponent;
  let fixture: ComponentFixture<DeleteGroupConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteGroupConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteGroupConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
