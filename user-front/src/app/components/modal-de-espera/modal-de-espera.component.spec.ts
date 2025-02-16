import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalDeEsperaComponent } from './modal-de-espera.component';

describe('ModalDeEsperaComponent', () => {
  let component: ModalDeEsperaComponent;
  let fixture: ComponentFixture<ModalDeEsperaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalDeEsperaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalDeEsperaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
