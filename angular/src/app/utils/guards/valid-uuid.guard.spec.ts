import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { validUuidGuard } from './valid-uuid.guard';

describe('validUuidGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => validUuidGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
