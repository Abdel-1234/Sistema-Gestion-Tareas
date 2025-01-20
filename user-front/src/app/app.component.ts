import { Component } from '@angular/core';
import { CreateUserComponent } from './components/create-user/create-user.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CreateUserComponent],
  template: `<app-create-user></app-create-user>`,
  styleUrls: ['./app.component.css'],
})
export class AppComponent {}
