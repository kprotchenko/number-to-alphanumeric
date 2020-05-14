import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NumberEditComponent } from './number-edit.component';

describe('NumberEditComponent', () => {
  let component: NumberEditComponent;
  let fixture: ComponentFixture<NumberEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NumberEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NumberEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
