import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {

  private url = 'http://localhost:8079/keycloak';

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<{ access_token: string }>(`${this.url}/login`, { username, password }).pipe(map(response => response.access_token));    
  }

  verifyToken(token: string) {
    console.log('token de para verificar', token);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<{ [key: string]: number }>(`${this.url}/roles`, { headers });
  }
}
