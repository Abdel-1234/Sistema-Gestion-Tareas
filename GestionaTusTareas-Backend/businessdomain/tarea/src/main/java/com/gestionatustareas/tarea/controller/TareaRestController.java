/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.gestionatustareas.tarea.controller;

import com.gestionatustareas.tarea.entities.Tarea;
import com.gestionatustareas.tarea.repository.TareaRepository;
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
@RequestMapping("/tarea")
public class TareaRestController {
    
    @Autowired
    TareaRepository tareaRepository;
    
    @GetMapping()
    public ResponseEntity<List<Tarea>> findAll() {
        List<Tarea> tareas=tareaRepository.findAll();
        if(tareas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(tareas);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(id);
        if(optionalTarea.isPresent()){
            return new ResponseEntity<>(optionalTarea.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/user/tareas")
    public ResponseEntity<List<Tarea>> findByPropietarioId(@RequestParam(name = "propietarioId")long propietarioId) {
        List<Tarea> tareas = tareaRepository.findByPropietarioId(propietarioId);
        
        if(tareas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(tareas);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody Tarea input) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(id);
        if(optionalTarea.isPresent()){
            Tarea newTarea = optionalTarea.get();
            newTarea.setTitulo(input.getTitulo());
            newTarea.setDescripcion(input.getDescripcion());
            newTarea.setEstado(input.getEstado());
            newTarea.setFechaVencimiento(input.getFechaVencimiento());
            newTarea.setPropietariosIds(input.getPropietariosIds());
            Tarea save = tareaRepository.save(newTarea);
            return new ResponseEntity<>(save,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Tarea input) {
        Tarea save = tareaRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Tarea> findById = tareaRepository.findById(id);
        if (findById.get() != null) {
            tareaRepository.delete(findById.get());
            return ResponseEntity.ok().build();
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
