/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.gestionatustareas.tarea.repository;

import com.gestionatustareas.tarea.entities.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



/**
 *
 * @author usuario
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    @Query("SELECT t FROM Tarea t WHERE :id MEMBER OF t.propietariosIds")
    public List<Tarea> findByPropietarioId(@Param("id")long id);
}
