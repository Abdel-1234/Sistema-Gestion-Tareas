/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.setups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 *
 * @author usuario
 */
@Slf4j
@Configuration
public class GlobalPostFiltering {
    @Bean
    public GlobalFilter potGlobalFiltering(){
        return(exchange, chain)->{
            return chain.filter(exchange)
              .then(Mono.fromRunnable(()->{
                  log.info("Global Post Filter ejecutado");
              }));
        };
    }
}
