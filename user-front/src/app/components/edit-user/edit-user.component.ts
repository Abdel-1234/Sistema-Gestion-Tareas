import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-edit-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent {

  @Input() showModal: Boolean = false;
  @Input() user: User ={nombre:"", email:""};

  @Output() closeModalForm = new EventEmitter<void>();
  @Output() refrescar = new EventEmitter<void>();

  constructor(private fb: FormBuilder, private userService: UserService) {
  }

  guardarUsuario(): void {
      console.log("Correcto");
      this.userService.updateUser(this.user!.id!, this.user!).subscribe({
        next: () => {
          alert('Usuario editado con éxito');
          this.refrescar.emit();
        },
        error: (err) => {
          alert('Error al editar el usuario');
          console.error(err);
        },
      });
  }
  closeModal(){
    this.showModal = false;
    this.closeModalForm.emit();
  }
}