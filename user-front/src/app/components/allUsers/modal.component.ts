import { Component, Input, Output, EventEmitter} from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { EditUserComponent } from '../edit-user/edit-user.component';

@Component({
  selector: 'app-modal',
  standalone: true,
  templateUrl: './modal.component.html',
  imports: [CommonModule, EditUserComponent],
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {
  @Input() data:  User[] | null = []; 
  @Output() refrescarLista = new EventEmitter<void>();
  showModal: boolean = false;
  UsuarioSelected: User = {nombre:"", email:""};
  
  constructor(private userService: UserService){ }
  closeModal(){
    this.data = null;
  }
  deleteUser(id: number): void {
    if(id != 0){
      this.userService.deleteUser(id).subscribe({
        next: () => {
          console.log(`Usuario con ID ${id} eliminado exitosamente.`);
          this.refrescar();
        },
        error: (err) => {
          console.error("Error al eliminar el usuario con id:" + id, err);
        }
      });
    }else{
      console.log('no se ha eliminado ya que id: ', id);
    }
  }
  
  closeModalForm(){
    this.showModal = false;
  }
  openModal(user: User){
    this.UsuarioSelected = user;
    this.showModal = true;
  }
  refrescar(){
    this.refrescarLista.emit();
  }
}
