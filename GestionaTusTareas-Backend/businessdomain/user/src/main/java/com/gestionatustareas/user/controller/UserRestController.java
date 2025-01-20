/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.gestionatustareas.user.controller;

import com.gestionatustareas.user.business.transactions.BussinesTransaction;
import com.gestionatustareas.user.entities.Usuario;
import com.gestionatustareas.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


/**
 *
 * @author usuario
 */
@Tag(name = "User API", description = "Esta API proporciona todas las funcionalidades para la gestión de usuarios.")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    BussinesTransaction bussinesTransaction;

    @Autowired
    private Environment env; 
    
    @GetMapping("/check")
    @Operation(description = "Devuelve el perfil que se utiliza", summary = "Devuelve null si no se encuentran datos")
    public String check() {
        return "Hello your proerty value is: "+ env.getProperty("custom.activeprofileName");
    }
    
    
    @Operation(description = "Devuelve todos los usuarios guardados", summary = "Devuelve 204 si no se encuentran datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Éxito"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping()
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }
    @Operation(description = "Devuelve todos los usuarios guardados con sus notificaciones y tareas correspondientes", summary = "Devuelve 204 si no se encuentran datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Éxito"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/full")
    public ResponseEntity<List<Usuario>> full() {
        return bussinesTransaction.full();
    }
    
    
    @Operation(
        description = "Obtiene un usuario por ID",
        summary = "Devuelve 404 si el usuario no existe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return bussinesTransaction.get(id);
    }
    
    @Operation(
        description = "Actualiza un usuario existente",
        summary = "Devuelve 404 si el usuario no existe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody Usuario input) {
        Optional<Usuario> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Usuario newUser = optionalUser.get();
            newUser.setEmail(input.getEmail());
            newUser.setNombre(input.getNombre());
            Usuario save = userRepository.save(newUser);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(
        description = "Crea un nuevo usuario",
        summary = "Devuelve 400 si la entrada es inválida"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Entrada inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Usuario input) {
        Usuario save = userRepository.save(input);
        return ResponseEntity.ok(save);
    }
    @Operation(
        description = "Elimina un usuario por ID",
        summary = "Devuelve 404 si el usuario no existe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Usuario> findById = userRepository.findById(id);
        if (findById.get() != null) {
            userRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

}
