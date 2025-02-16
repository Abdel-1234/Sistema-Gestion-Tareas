/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.setups;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author usuario
 */
@Slf4j
@Component
public class AuthenticationFiltering extends AbstractGatewayFilterFactory<AuthenticationFiltering.Config>{
    
    @Autowired
    private  WebClient.Builder webclientBuilder;
    
    public AuthenticationFiltering() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {  
             if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta el encabezado de autorización");
             }
             
             
             String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    String[] parts = authHeader.split(" ");
                    if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mala estructura de autorización");
                    }                      
                    
                    return  webclientBuilder.build()
                            .get()
                            .uri("http://KEYCLOACK/keycloak/roles").header(HttpHeaders.AUTHORIZATION, parts[1])                           
                            .retrieve()
                             .bodyToMono(JsonNode.class)
                             .map(response -> {  
                                 if(response != null){
                                   log.info("See Objects: " + response);                                
                                   if(response.get("admin") == null || StringUtils.isEmpty(response.get("admin").asText())){
                                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta el rol de administrador");
                                   }
                                 }else{
                                     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Faltan roles");
                                 }
                             return exchange;
                     })
                       .onErrorMap(error -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error de comnicacion", error.getCause());})
                      .flatMap(chain::filter);                                                     
                },1); 
    }
    public static class Config{
        
    }
}
