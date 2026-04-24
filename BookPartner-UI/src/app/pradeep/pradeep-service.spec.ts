import { TestBed } from '@angular/core/testing';

import { PradeepService } from './pradeep-service';

describe('PradeepService', () => {
  let service: PradeepService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PradeepService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
