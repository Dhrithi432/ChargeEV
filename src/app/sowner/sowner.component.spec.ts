import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SownerComponent } from './sowner.component';

describe('SownerComponent', () => {
  let component: SownerComponent;
  let fixture: ComponentFixture<SownerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SownerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SownerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
