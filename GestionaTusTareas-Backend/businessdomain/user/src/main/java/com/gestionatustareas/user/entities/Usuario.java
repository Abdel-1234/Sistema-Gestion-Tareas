/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.user.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.List;

import lombok.Data;

/**
 *
 * @author usuario
 */

@Entity
@Data
@Schema(name = "user", description = "Entidad que representa a un usuario en el sistema")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(name = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "0", description = "ID del usuario. No es necesario proporcionarlo ya que se autogenera.")
    private long id;
    
    @Schema(name = "nombre", requiredMode = Schema.RequiredMode.REQUIRED, example = "Javier Sanchez Rodriguez", defaultValue = "nombre", description = "Nombre completo del usuario")
    private String nombre;
    
    @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED, example = "isacherrero@gmail.com", defaultValue = "user@gmail.com", description = "Email del usuario")
    private String email;
    
    @Transient
    @Schema(name = "tareas", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Lista de tareas del usuario. No se persisten en la base de datos")
    private List<?> tareas;

    @Transient
    @Schema(name = "notificaciones", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Lista de notificaciones del usuario. No se persisten en la base de datos")
    private List<?> notificaciones;

}
