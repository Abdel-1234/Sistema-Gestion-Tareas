package com.gestionatustareas.user.controller;

import com.gestionatustareas.user.entities.Usuario;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.gestionatustareas.user.repository.UserRepository;
import com.gestionatustareas.user.business.transactions.BussinesTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
public class UserRestControllerUnitarioTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BussinesTransaction bussinesTransaction;
    
    @Mock
    private Environment env;

    @InjectMocks
    private UserRestController userRestController;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Javier Sanchez");
        usuario.setEmail("javier@gmail.com");
    }

    @Test
    public void testGet() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(usuario));

        var result = userRestController.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Javier Sanchez", result.get(0).getNombre());
    }

    @Test
    public void testGetById() {
        when(bussinesTransaction.get(1L)).thenReturn(ResponseEntity.ok(usuario));

        var result = userRestController.get(1L);
        assertNotNull(result);
        assertEquals("Javier Sanchez", result.getBody().getNombre());
        assertEquals("javier@gmail.com", result.getBody().getEmail());
    }

    @Test
    public void testPost() {
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);
        
        var result = userRestController.post(usuario);
        assertNotNull(result);
        assertEquals("Javier Sanchez", result.getBody().getNombre());
        assertEquals("javier@gmail.com", result.getBody().getEmail());
    }

    @Test
    public void testUpdate() {
        Usuario updatedUser = new Usuario();
        updatedUser.setNombre("Juan");
        updatedUser.setEmail("juan@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(userRepository.save(any(Usuario.class))).thenReturn(updatedUser);

        var result = userRestController.put(1L, updatedUser);
        assertNotNull(result);
        assertEquals("Juan", result.getBody().getNombre());
        assertEquals("juan@gmail.com", result.getBody().getEmail());
       
    }

    @Test
    public void testDelete() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var result = userRestController.delete(1L);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository, times(1)).delete(usuario);
    }

    @Test
    public void testCheck() {
        when(env.getProperty("custom.activeprofileName")).thenReturn("local");
        var result = userRestController.check();
        assertNotNull(result);
        assertEquals("El perfil que utilizas es: local", result);
    }
}
