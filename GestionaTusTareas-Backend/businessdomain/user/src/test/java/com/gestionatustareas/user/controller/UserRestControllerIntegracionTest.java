/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.user.controller;

/**
 *
 * @author usuario
 */
import com.gestionatustareas.user.entities.Usuario;
import com.gestionatustareas.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SpringBootTest
public class UserRestControllerIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setEmail("testuser@example.com");
        userRepository.save(usuario);
    }

    @Test
    public void testCheck() throws Exception {
        mockMvc.perform(get("/user/check"))
                .andExpect(status().isOk())
                .andExpect(content().string("El perfil que utilizas es: local"));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/user/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    public void testPost() throws Exception {
        String userJson = "{\"nombre\":\"New User\",\"email\":\"newuser@example.com\"}";

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("New User"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    public void testPut() throws Exception {
        String updatedUserJson = "{\"nombre\":\"Updated User\",\"email\":\"updateduser@example.com\"}";

        mockMvc.perform(put("/user/{id}", usuario.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updateduser@example.com"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/user/{id}", usuario.getId()))
                .andExpect(status().isOk());
    }
}
