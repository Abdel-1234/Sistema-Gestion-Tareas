package com.gestionatustareas.controler;

import com.gestionatustareas.service.KeycloakRestService;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwk.Jwk;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gestionatustareas.exception.BussinesRuleException;
import com.gestionatustareas.service.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private KeycloakRestService restService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestHeader("Authorization") String authHeader) throws BussinesRuleException {
        try {
            DecodedJWT jwt = JWT.decode(authHeader.replace("Bearer", "").trim());

            // Verificar que el JWT es válido
            Jwk jwk = jwtService.getJwk();
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);

            // Obtener roles del JWT
            List<String> roles = (List<String>) jwt.getClaim("realm_access").asMap().get("roles");

            // Verificar que el token no ha expirado
            Date expiryDate = jwt.getExpiresAt();
            if (expiryDate.before(new Date())) {
                throw new Exception("Token is expired");
            }

            // Validación pasada, construir respuesta
            Map<String, Integer> rolesWithLengths = new HashMap<>();
            for (String role : roles) {
                rolesWithLengths.put(role, role.length());
            }

            return ResponseEntity.ok(rolesWithLengths);
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            throw new BussinesRuleException("01", e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/valid")
    public ResponseEntity<?> valid(@RequestHeader("Authorization") String authHeader) throws BussinesRuleException {
        try {
            restService.checkValidity(authHeader);
            Map<String, String> response = new HashMap<>();
            response.put("is_valid", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Token is not valid, exception: {}", e.getMessage());
            throw new BussinesRuleException("is_valid", "False", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(String username, String password) {
        String loginResponse = restService.login(username, password);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestParam("refresh_token") String refreshToken) throws BussinesRuleException {
        try {
            restService.logout(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("logout", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Unable to logout, exception: {}", e.getMessage());
            throw new BussinesRuleException("logout", "False", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refresh(@RequestParam("refresh_token") String refreshToken) throws BussinesRuleException {
        try {
            return ResponseEntity.ok(restService.refresh(refreshToken));
        } catch (Exception e) {
            logger.error("Unable to refresh, exception: {}", e.getMessage());
            throw new BussinesRuleException("refresh", "False", HttpStatus.FORBIDDEN);
        }
    }
}
