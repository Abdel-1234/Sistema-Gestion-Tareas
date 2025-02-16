import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { User } from '../models/user';

fdescribe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const url = 'http://localhost:8080/user';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Usamos HttpClientTestingModule para mockear las peticiones HTTP
      providers: [UserService],
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Verifica que no haya solicitudes HTTP pendientes
  });

  it('debería crearse el servicio', () => {
    expect(service).toBeTruthy();
  });

  fdescribe('createUser', () => {
    it('debería crear un usuario', () => {
      const newUser: User = { id: 3, nombre: 'abdel', email: 'abdel.oubr@gmail.com' };

      service.createUser(newUser).subscribe(user => {
        expect(user).toEqual(newUser); // Verifica que el usuario creado es el esperado
      });

      const req = httpMock.expectOne(url);
      expect(req.request.method).toBe('POST'); // Verifica que el método de la solicitud sea POST
      req.flush(newUser); // Simula la respuesta del servidor
    });
  });

  fdescribe('getUsers', () => {
    it('debería obtener la lista de usuarios', () => {
      const mockUsers: User[] = [
        { id: 3, nombre: 'abdel', email: 'abdel.oubr@gmail.com' },
        { id: 2, nombre: 'abdel2', email: 'abdel2.oubr@gmail.com' }
      ];

      service.getUsers().subscribe(users => {
        expect(users).toEqual(mockUsers); // Verifica que los usuarios obtenidos sean los esperados
      });

      const req = httpMock.expectOne(url);
      expect(req.request.method).toBe('GET'); // Verifica que el método de la solicitud sea GET
      req.flush(mockUsers); // Simula la respuesta del servidor
    });
  });

  fdescribe('getUser', () => {
    it('debería obtener un usuario por ID', () => {
      const mockUser: User = { id: 1, nombre: 'abdel', email: 'abdel.oubr@gmail.com' };

      service.getUser(1).subscribe(user => {
        expect(user).toEqual(mockUser); // Verifica que el usuario obtenido es el esperado
      });

      const req = httpMock.expectOne(`${url}/1`);
      expect(req.request.method).toBe('GET'); // Verifica que el método de la solicitud sea GET
      req.flush(mockUser); // Simula la respuesta del servidor
    });
  });

  fdescribe('updateUser', () => {
    it('debería actualizar un usuario', () => {
      const updatedUser: User = { id: 1, nombre: 'abdel', email: 'abdel.oubr@gmail.com' };

      service.updateUser(1, updatedUser).subscribe(user => {
        expect(user).toEqual(updatedUser); // Verifica que el usuario actualizado es el esperado
      });

      const req = httpMock.expectOne(`${url}/1`);
      expect(req.request.method).toBe('PUT'); // Verifica que el método de la solicitud sea PUT
      req.flush(updatedUser); // Simula la respuesta del servidor
    });
  });

  fdescribe('deleteUser', () => {
    it('debería eliminar un usuario', () => {
      service.deleteUser(1).subscribe(response => {
        expect(response).toBeUndefined(); // Verifica que la respuesta sea undefined después de eliminar
      });

      const req = httpMock.expectOne(`${url}/1`);
      expect(req.request.method).toBe('DELETE'); // Verifica que el método de la solicitud sea DELETE
      req.flush(null); // Simula la respuesta del servidor
    });
  });
});
