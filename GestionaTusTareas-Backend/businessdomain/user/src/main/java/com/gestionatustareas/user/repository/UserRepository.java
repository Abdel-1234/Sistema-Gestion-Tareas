/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.gestionatustareas.user.repository;

import com.gestionatustareas.user.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 *
 * @author usuario
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    
}
