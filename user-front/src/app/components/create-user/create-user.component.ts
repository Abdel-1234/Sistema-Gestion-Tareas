import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
})
export class CreateUserComponent {
  userForm: FormGroup;
  @Input() showModal: Boolean = false;
  @Output() closeModalForm = new EventEmitter<void>();

  constructor(private fb: FormBuilder, private userService: UserService) {
    this.userForm = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      console.log("Correcto");
      const user: User = this.userForm.value;
      this.userService.createUser(user).subscribe({
        next: (response) => {
          alert('Usuario creado con éxito');
          this.userForm.reset(); 
        },
        error: (err) => {
          alert('Error al crear el usuario');
          console.error(err);
        },
      });
    } else {
      alert('Completa los campos correctamente');
    }
  }
  closeModal(){
    this.showModal = false;
    this.closeModalForm.emit();
  }
}
