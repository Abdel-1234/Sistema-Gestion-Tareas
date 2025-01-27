import { TestBed } from '@angular/core/testing';

import { OpenaiService } from './huggingface_IA.service';

describe('OpenaiService', () => {
  let service: OpenaiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpenaiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
