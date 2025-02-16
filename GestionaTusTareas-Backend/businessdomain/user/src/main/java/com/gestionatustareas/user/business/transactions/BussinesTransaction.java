/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.user.business.transactions;

import com.gestionatustareas.user.entities.Usuario;
import com.gestionatustareas.user.repository.UserRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author sotobotero
 */
@Service
public class BussinesTransaction {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    UserRepository userRepository;

    HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .responseTimeout(Duration.ofSeconds(5))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    private List<?> getTareas(long idPropietario) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://BUSINESSDOMAIN-TAREA/tarea")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://BUSINESSDOMAIN-TAREA/tarea"))
                .build();
        Optional<List<?>> tareasOptional = Optional.ofNullable(build.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                .path("/user/tareas")
                .queryParam("propietarioId", idPropietario)
                .build())
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block());
        return tareasOptional.orElse(Collections.emptyList());

    }

    private List<?> getNotificaciones(long usuario_id) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://BUSINESSDOMAIN-NOTIFICACION/notificacion")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://BUSINESSDOMAIN-NOTIFICACION/notificacion"))
                .build();
        Optional<List<?>> notificacionesOptional = Optional.ofNullable(build.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                .path("/user/notificaciones")
                .queryParam("usuario_id", usuario_id)
                .build())
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block());
        return notificacionesOptional.orElse(Collections.emptyList());

    }
    public ResponseEntity<List<Usuario>> full() {

        List<Usuario> usuarios = userRepository.findAll();

        usuarios.forEach(usuario -> {
            List<?> tareas = getTareas(usuario.getId());
            usuario.setTareas(tareas);

            List<?> notificaciones = getNotificaciones(usuario.getId());
            usuario.setNotificaciones(notificaciones);
        });

        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(usuarios);
        }
    }
    public ResponseEntity<Usuario> get(long id) {
        Optional<Usuario> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            List<?> tareas = getTareas(optionalUser.get().getId());
            optionalUser.get().setTareas(tareas);
            List<?> notificaciones = getNotificaciones(optionalUser.get().getId());
            optionalUser.get().setNotificaciones(notificaciones);
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
