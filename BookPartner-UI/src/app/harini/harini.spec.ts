import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Harini } from './harini';

describe('Harini', () => {
  let component: Harini;
  let fixture: ComponentFixture<Harini>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Harini],
    }).compileComponents();

    fixture = TestBed.createComponent(Harini);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
