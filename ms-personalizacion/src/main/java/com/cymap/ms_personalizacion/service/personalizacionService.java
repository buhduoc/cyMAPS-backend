package com.cymap.ms_personalizacion.service;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.repository.personalizacionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class personalizacionService {

    @Autowired
    private personalizacionRepository repo;

    private final WebClient webClientUsuarios = WebClient.builder()
            .baseUrl("http://localhost:8080/api/usuarios")
            .build();

    @CircuitBreaker(name = "personalizacionCB", fallbackMethod = "fallbackRegistrar")
    public Mono<personalizacionModel> guardarPreferencias(personalizacionModel p) {
        System.out.println("Usuario Validacion");
        System.out.println("Validando Usuario ID: " + p.getUsuarioId());

        return webClientUsuarios.get()
                .uri("/{id}", p.getUsuarioId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("El usuario con ID " + p.getUsuarioId() + " no existe en el sistema."))
                )
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true)
                .flatMap(existe -> {
                    System.out.println("Usuario verificado con éxito. Procesando persistencia...");

                    Optional<personalizacionModel> configExistente = repo.findByUsuarioId(p.getUsuarioId());

                    personalizacionModel entidadAGuardar;

                    if (configExistente.isPresent()) {
                        entidadAGuardar = configExistente.get();
                        entidadAGuardar.setColorRutaHex(p.getColorRutaHex());
                        entidadAGuardar.setModoOscuro(p.isModoOscuro());
                        entidadAGuardar.setVistaMapaDefecto(p.getVistaMapaDefecto());
                    } else {
                        entidadAGuardar = p;
                    }
                    personalizacionModel guardado = repo.save(entidadAGuardar);
                    return Mono.just(guardado);
                });
    }

    public Mono<personalizacionModel> fallbackRegistrar(personalizacionModel p, Throwable t) {
        System.err.println("fallback activado");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }
        return Mono.error(new RuntimeException("El servicio de verificación de usuarios no está disponible en este momento."));
    }

    public List<personalizacionModel> listar() {
        return repo.findAll();
    }

    public personalizacionModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public personalizacionModel obtenerPorUsuarioId(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).orElse(null);
    }

    public personalizacionModel actualizar(Long id, personalizacionModel nuevasPreferencias) {
        Optional<personalizacionModel> configExistente = repo.findById(id);

        if (configExistente.isPresent()) {
            personalizacionModel p = configExistente.get();

            p.setUsuarioId(nuevasPreferencias.getUsuarioId());
            p.setColorRutaHex(nuevasPreferencias.getColorRutaHex());
            p.setModoOscuro(nuevasPreferencias.isModoOscuro());
            p.setVistaMapaDefecto(nuevasPreferencias.getVistaMapaDefecto());
            return repo.save(p);
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