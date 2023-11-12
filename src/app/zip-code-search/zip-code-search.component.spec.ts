import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZipCodeSearchComponent } from './zip-code-search.component';

describe('ZipCodeSearchComponent', () => {
  let component: ZipCodeSearchComponent;
  let fixture: ComponentFixture<ZipCodeSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZipCodeSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZipCodeSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
