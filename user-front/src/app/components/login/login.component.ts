import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AutenticacionService } from '../../services/autenticacion.service';
import { ModalDeEsperaComponent } from '../modal-de-espera/modal-de-espera.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true, 
  imports: [FormsModule, ReactiveFormsModule, ModalDeEsperaComponent,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})



export class LoginComponent {
  username = '';
  password = '';

  showModal= false;

  constructor(private autenticacionService: AutenticacionService, private router: Router) {}

  login() {
    this.openModal();
    console.log("Login enviado");
    this.autenticacionService.login(this.username, this.password).subscribe((accessToken: string) => {
      console.log("Entro en correctamente");
      console.log(accessToken);
      localStorage.setItem('token', accessToken);
      this.verifyAccess();
      this.closeModal();
    }, error => {
      console.log(error);
      alert('Error de autenticación');
      this.closeModal();
    });
  }

  verifyAccess() {
    const token = localStorage.getItem('token');
    if (!token) return;
    console.log(token);
    this.autenticacionService.verifyToken(token).subscribe(response => {
      if (response && 'admin' in response) {
        this.router.navigate(['/chatbot']);
      } else {
        localStorage.removeItem('token');
        alert('Token invalido');
      }
    });
  }

  openModal(){
    this.showModal=true;
  }
  closeModal(){
    this.showModal=false;
  }
}

