import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImmutableDrawComponent } from './immutable-draw.component';

describe('ImmutableDrawComponent', () => {
  let component: ImmutableDrawComponent;
  let fixture: ComponentFixture<ImmutableDrawComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImmutableDrawComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImmutableDrawComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
