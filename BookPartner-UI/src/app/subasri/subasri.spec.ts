import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Subasri } from './subasri';

describe('Subasri', () => {
  let component: Subasri;
  let fixture: ComponentFixture<Subasri>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Subasri],
    }).compileComponents();

    fixture = TestBed.createComponent(Subasri);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
