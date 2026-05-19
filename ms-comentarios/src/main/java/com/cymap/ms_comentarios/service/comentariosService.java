package com.cymap.ms_comentarios.service;

import com.cymap.ms_comentarios.model.comentariosModel;
import com.cymap.ms_comentarios.repository.comentariosRepository;
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
public class comentariosService {

    @Autowired
    private comentariosRepository repo;

    @Autowired
    @Qualifier("webClientUsuarios")
    private WebClient webClientUsuarios;

    @Autowired
    @Qualifier("webClientRutas")
    private WebClient webClientRutas;

    @CircuitBreaker(name = "usuariosCB", fallbackMethod = "fallbackRegistrar")
    public comentariosModel registrar(comentariosModel c) {

        Boolean existeUsuario = webClientUsuarios.get()
                .uri("/{id}", c.getUsuarioId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("El usuario con ID " + c.getUsuarioId() + " no existe."))
                )
                .bodyToMono(Object.class)
                .map(obj -> true)
                .block();

        Boolean existeRuta = webClientRutas.get()
                .uri("/{id}", c.getRutaId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("La ruta con ID " + c.getRutaId() + " no existe."))
                )
                .bodyToMono(Object.class)
                .map(obj -> true)
                .block();

        if (Boolean.TRUE.equals(existeUsuario) && Boolean.TRUE.equals(existeRuta)) {
            return repo.save(c);
        }
        throw new RuntimeException("No se pudo registrar el comentario por inconsistencia de datos.");
    }

    public comentariosModel fallbackRegistrar(comentariosModel c, Throwable t) {
        if (t.getMessage() != null && (t.getMessage().contains("no existe"))) {
            throw new RuntimeException("Error de validación: " + t.getMessage());
        }
        throw new RuntimeException("Lo sentimos, el sistema de validación no está disponible temporalmente. Inténtalo más tarde.");
    }
    public List<comentariosModel> listar() {
        return repo.findAll();
    }

    public comentariosModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<comentariosModel> obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId);
    }

    public comentariosModel actualizar(Long id, comentariosModel nuevosDatos) {
        Optional<comentariosModel> comentarioExistente = repo.findById(id);

        if (comentarioExistente.isPresent()) {
            comentariosModel c = comentarioExistente.get();
            c.setRutaId(nuevosDatos.getRutaId());
            c.setUsuarioId(nuevosDatos.getUsuarioId());
            c.setContenido(nuevosDatos.getContenido());
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