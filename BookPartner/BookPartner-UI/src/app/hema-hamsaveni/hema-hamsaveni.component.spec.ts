import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HemaHamsaveni } from './hema-hamsaveni';

describe('HemaHamsaveni', () => {
  let component: HemaHamsaveni;
  let fixture: ComponentFixture<HemaHamsaveni>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HemaHamsaveni]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HemaHamsaveni);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
