package com.cymap.ms_comunidad.service;

import com.cymap.ms_comunidad.model.comunidadModel;
import com.cymap.ms_comunidad.repository.comunidadRepository;
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
public class comunidadService {

    @Autowired
    private comunidadRepository repo;

    @Autowired
    @Qualifier("webClientUsuarios")
    private WebClient webClientUsuarios;

    @CircuitBreaker(name = "comunidadCB", fallbackMethod = "fallbackRegistrar")
    public Mono<comunidadModel> registrarReactivo(comunidadModel c) {
        System.out.println("=== VALIDANDO USUARIO EN MS-COMUNIDAD ===");
        System.out.println("Validando Usuario ID: " + c.getUsuarioId());

        return webClientUsuarios.get()
                .uri("/{id}", c.getUsuarioId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("El usuario con ID " + c.getUsuarioId() + " no existe en el sistema."))
                )
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true)
                .flatMap(existe -> {
                    System.out.println("Usuario verificado con éxito. Guardando comunidad...");
                    comunidadModel guardado = repo.save(c);
                    return Mono.just(guardado);
                });
    }

    public Mono<comunidadModel> fallbackRegistrar(comunidadModel c, Throwable t) {
        System.err.println("FALLBACK ACTIVADO EN MS-COMUNIDAD");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }
        return Mono.error(new RuntimeException("El servicio de verificación de usuarios no está disponible en este momento."));
    }

    public comunidadModel registrar(comunidadModel c) {
        return repo.save(c);
    }

    public List<comunidadModel> listar() {
        return repo.findAll();
    }

    public comunidadModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<comunidadModel> obtenerPorUsuarioId(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public comunidadModel actualizar(Long id, comunidadModel nuevosDatos) {
        Optional<comunidadModel> comunidadExistente = repo.findById(id);

        if (comunidadExistente.isPresent()) {
            comunidadModel c = comunidadExistente.get();
            c.setNombreGrupo(nuevosDatos.getNombreGrupo());
            c.setDescripcion(nuevosDatos.getDescripcion());
            c.setUsuarioId(nuevosDatos.getUsuarioId());
            return repo.save(c);
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