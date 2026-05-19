package com.cymap.ms_geolocalizacion.service;

import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import com.cymap.ms_geolocalizacion.repository.geolocalizacionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class geolocalizacionService {

    @Autowired
    private geolocalizacionRepository repo;

    @Autowired
    @Qualifier("webClientUsuarios")
    private WebClient webClientUsuarios;

    @CircuitBreaker(name = "geolocalizacionCB", fallbackMethod = "fallbackRegistrar")
    public Mono<geolocalizacionModel> registrarReactivo(geolocalizacionModel g) {
        System.out.println("VALIDANDO USUARIO");
        System.out.println("Validando Usuario ID: " + g.getUsuarioId());

        return webClientUsuarios.get()
                .uri("/{id}", g.getUsuarioId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("El usuario con ID " + g.getUsuarioId() + " no existe en el sistema."))
                )
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true)
                .flatMap(existe -> {
                    System.out.println("Usuario verificado con éxito. Guardando coordenada...");
                    geolocalizacionModel guardado = repo.save(g);
                    return Mono.just(guardado);
                });
    }

    public Mono<geolocalizacionModel> fallbackRegistrar(geolocalizacionModel g, Throwable t) {
        System.err.println("FALLBACK ACTIVADO");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }
        return Mono.error(new RuntimeException("El servicio de verificación de usuarios no está disponible en este momento."));
    }

    public geolocalizacionModel registrar(geolocalizacionModel g) {
        return repo.save(g);
    }

    public List<geolocalizacionModel> listar() {
        return repo.findAll();
    }

    public geolocalizacionModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public geolocalizacionModel obtenerUltimaUbicacion(Long usuarioId) {
        return repo.findTopByUsuarioIdOrderByIdDesc(usuarioId).orElse(null);
    }

    public geolocalizacionModel actualizar(Long id, geolocalizacionModel nuevosDatos) {
        Optional<geolocalizacionModel> posicionExistente = repo.findById(id);

        if (posicionExistente.isPresent()) {
            geolocalizacionModel g = posicionExistente.get();
            g.setUsuarioId(nuevosDatos.getUsuarioId());
            g.setLatitud(nuevosDatos.getLatitud());
            g.setLongitud(nuevosDatos.getLongitud());
            return repo.save(g);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}