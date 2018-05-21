import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MutableDrawComponent } from './mutable-draw.component';

describe('MutableDrawComponent', () => {
  let component: MutableDrawComponent;
  let fixture: ComponentFixture<MutableDrawComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MutableDrawComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MutableDrawComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
