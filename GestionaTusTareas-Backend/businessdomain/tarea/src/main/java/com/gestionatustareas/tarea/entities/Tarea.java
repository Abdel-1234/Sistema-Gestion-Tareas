/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.tarea.entities;

import com.gestionatustareas.tarea.enums.EstadoTarea;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author usuario
 */

@Entity
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    private String titulo;
    
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;
    
    @CreatedDate
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaVencimiento;
    
    @ElementCollection
    private List<Long> propietariosIds;
}
