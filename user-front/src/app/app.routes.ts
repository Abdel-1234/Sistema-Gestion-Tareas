import { Routes } from '@angular/router';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';

export const routes: Routes = [
    { path: 'chatbot', component:  ChatbotComponent}, 
    { path: 'create-user', component: CreateUserComponent } 
];
