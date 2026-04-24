import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Pradeep } from './pradeep';

describe('Pradeep', () => {
  let component: Pradeep;
  let fixture: ComponentFixture<Pradeep>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Pradeep],
    }).compileComponents();

    fixture = TestBed.createComponent(Pradeep);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
