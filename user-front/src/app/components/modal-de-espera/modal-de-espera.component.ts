import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-modal-de-espera',
  standalone: true,
  imports: [MatProgressSpinnerModule, CommonModule],
  templateUrl: './modal-de-espera.component.html',
  styleUrl: './modal-de-espera.component.css'
})
export class ModalDeEsperaComponent {

}
  