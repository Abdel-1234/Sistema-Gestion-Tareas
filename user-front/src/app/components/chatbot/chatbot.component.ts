import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { ModalComponent } from '../allUsers/modal.component';
import { Message } from '../../interfaces/message';
import { User } from '../../models/user';
import { CreateUserComponent } from '../create-user/create-user.component';
import { ChatbotService } from '../../services/huggingface_IA.service';
import { RouterModule } from '@angular/router';
import {  Router } from '@angular/router';



@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule, ModalComponent, CreateUserComponent, RouterModule], 
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css'],
})
export class ChatbotComponent {
  messages: Message[] = [];
  messagesChatBot: Message[] = [];
  userMessage: string = ''; 
  userMessageIA: string = '';
  respuestaBot: string = '';
  usuarios: User[]  = [];
  showModal: boolean = false;
  showModalForm: boolean = false;

  constructor(private userService: UserService, private openAIService: ChatbotService, private router: Router) {
    this.messages.push({ sender: 'bot', text: 'Dime que operación quieres que ejecute 🙂' });
    this.messagesChatBot.push({ sender: 'bot', text: 'Hola, dime algo 🙂' });

  }


  entradaMensajeBotIA() {
    const userMessage = this.userMessageIA.trim();
    if (!userMessage) return; 
    this.messagesChatBot.push({ sender: 'user', text: userMessage });
    
    try {
      this.openAIService.getResponse(userMessage).then((response) => {
        console.error('Entro');
          this.respuestaBot = response[0]?.generated_text || 'No response';
          console.log(this.respuestaBot)
          this.messagesChatBot.push({ sender: 'botIA', text: this.respuestaBot });
      }).catch((error) => {
          this.respuestaBot = 'Error al obtener la respuesta';
          console.error(error); 
      });
  } catch (error) {
      this.respuestaBot = 'Error al obtener la respuesta';
      console.error(error);
  }
    this.userMessageIA = ''; 
  }

  entradaMensaje() {
    const userMessage = this.userMessage.trim(); 
    console.log('Mensaje del usuario:', userMessage); 

    if (!userMessage) return; 
    this.messages.push({ sender: 'user', text: userMessage });
    

    this.logicaMensaje(userMessage);
    this.userMessage = ''; 
  }

  logicaMensaje(message: string) {
    this.messages.push({ sender: 'bot', text: 'Escribiendo...' });
    if (message.toLowerCase().includes('usuarios')) {
      this.userService.getUsers().subscribe(
        (data) => {
          this.messages.pop();
          const users = JSON.stringify(data);
          const userss = JSON.parse(users);
          this.usuarios = [];
          userss.forEach((user: { id: number; nombre: string; email: string; }) => {
            this.usuarios.push(user);
          });
          this.openModal(this.usuarios)
          this.messages.push({ sender: 'bot', text: 'Te ayudo en algo más?' });
        },
        (error) => {
          if (error.status === 404) {
            alert('No autorizado, su sesión ha caducado, debe volver a loguease. :)');
          }
          localStorage.removeItem('token');
          console.error('Error al obtener usuarios:', error);
          this.messages.pop();
          this.messages.push({ sender: 'bot', text: 'No pude obtener los usuarios, intentalo más tarde.' });
          this.router.navigate(['/login']);
        }
      );
    } else if(message.toLowerCase().includes('crear') && message.toLowerCase().includes('usuario') ){
      this.messages.pop();
      this.messages.push({ sender: 'bot', text: 'Te ayudo en algo más?' });
      this.openModalForm();
    } else{

      this.messages.pop();
      this.messages.push({ sender: 'bot', text: 'No entiendo esa solicitud.' });
    }
  }
  refrescarLista(){
    this.userService.getUsers().subscribe(
      (data) => {
        this.messages.pop();
        const users = JSON.stringify(data);
        const userss = JSON.parse(users);
        this.usuarios = [];
        userss.forEach((user: { id: number; nombre: string; email: string; }) => {
          this.usuarios.push(user);
        });
        this.openModal(this.usuarios)
        this.messages.push({ sender: 'bot', text: 'Te ayudo en algo más?' });
      },
      (error) => {
        console.error('Error al obtener los usuarios:', error);
        this.messages.pop();
        this.messages.push({ sender: 'bot', text: 'No pude obtener los usuarios, intenta más tarde' });
      });
  }

  openModal(usuarios: User[]): void {
    this.usuarios = usuarios;
    this.showModal = true; 
  }
  openModalForm(): void {
    this.showModalForm = true; 
  }
  closeModalForm(){
    this.showModalForm = false;
  }
}
