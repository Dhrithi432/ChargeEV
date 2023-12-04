import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Signup1Component } from './signup1.component';

describe('Signup1Component', () => {
  let component: Signup1Component;
  let fixture: ComponentFixture<Signup1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Signup1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Signup1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
