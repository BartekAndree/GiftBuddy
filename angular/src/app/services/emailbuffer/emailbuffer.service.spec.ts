import { TestBed } from '@angular/core/testing';

import { EmailbufferService } from './emailbuffer.service';

describe('EmailbufferService', () => {
  let service: EmailbufferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmailbufferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
