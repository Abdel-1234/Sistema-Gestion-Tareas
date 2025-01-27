import { Component } from '@angular/core';
import { ChatbotComponent } from './components/chatbot/chatbot.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ChatbotComponent],
  template: '<app-chatbot></app-chatbot>', 
  styleUrls: ['./app.component.css'],
})
export class AppComponent {}
