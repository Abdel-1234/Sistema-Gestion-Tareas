/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.gestionatustareas.user.repository;

import com.gestionatustareas.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 *
 * @author usuario
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
}
