/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.notificacion.entities;

import com.gestionatustareas.notificacion.enums.TipoNotificacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author usuario
 */

@Entity
@Data
public class Notificacion {
     @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
     
    private long usuario_id;
    
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;
    
    private String mensaje;
    
    @CreatedDate
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaVencimiento;
    
    @Column(columnDefinition = "booleano por defecto false")
    private boolean leido = Boolean.FALSE;

}
