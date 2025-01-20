/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.gestionatustareas.notificacion.repository;

import com.gestionatustareas.notificacion.entities.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



/**
 *
 * @author usuario
 */
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("SELECT n FROM Notificacion n WHERE n.usuario_id = ?1")
    public List<Notificacion> findByUsuarioId(long usuario_id);
}
