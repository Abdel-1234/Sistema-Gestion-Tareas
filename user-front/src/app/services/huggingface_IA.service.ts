import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {
  private apiUrl = 'https://api-inference.huggingface.co/models/gpt2';
  private apiKey = 'Secreta';

  constructor() { }

  async getResponse(inputText: string) {
    try {
      const response = await axios.post(
        this.apiUrl,
        {
          inputs: inputText
        },
        {
          headers: {
            'Authorization': `Bearer ${this.apiKey}`,
            'Content-Type': 'application/json'
          }
        }
      );
      return response.data;
    } catch (error) {
      console.error('Error al obtener respuesta de la API:', error);
      throw error;
    }
  }
}
