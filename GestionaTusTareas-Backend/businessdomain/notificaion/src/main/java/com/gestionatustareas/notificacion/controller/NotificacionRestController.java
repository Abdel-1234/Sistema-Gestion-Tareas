/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.gestionatustareas.notificacion.controller;

import com.gestionatustareas.notificacion.entities.Notificacion;
import com.gestionatustareas.notificacion.repository.NotificacionRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/notificacion")
public class NotificacionRestController {
    
    @Autowired
    NotificacionRepository notificacionRepository;
    
    @GetMapping()
    public ResponseEntity<List<Notificacion>> findAll() {
        List<Notificacion> notificaciones=notificacionRepository.findAll();
        if(notificaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(notificaciones);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<Notificacion> optionalNotificacion = notificacionRepository.findById(id);
        if(optionalNotificacion.isPresent()){
            return new ResponseEntity<>(optionalNotificacion.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/user/notificaciones")
    public ResponseEntity<List<Notificacion>> findByUsuarioId(@RequestParam(name = "usuario_id")long usuario_id) {
        List<Notificacion> notificaciones =  notificacionRepository.findByUsuarioId(usuario_id);
        if(notificaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(notificaciones);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody Notificacion input) {
        Optional<Notificacion> optionalNotificacion = notificacionRepository.findById(id);
        if(optionalNotificacion.isPresent()){
            Notificacion newNotificacion = optionalNotificacion.get();
            newNotificacion.setUsuario_id(input.getUsuario_id());
            newNotificacion.setTipo(input.getTipo());
            newNotificacion.setMensaje(input.getMensaje());
            newNotificacion.setFechaVencimiento(input.getFechaVencimiento());
            newNotificacion.setLeido(input.isLeido());
            Notificacion save = notificacionRepository.save(newNotificacion);
            return new ResponseEntity<>(save,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Notificacion input) {
        Notificacion save = notificacionRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Notificacion> findById = notificacionRepository.findById(id);
        if (findById.get() != null) {
            notificacionRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
