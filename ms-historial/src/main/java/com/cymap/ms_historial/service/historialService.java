package com.cymap.ms_historial.service;

import com.cymap.ms_historial.model.historialModel;
import com.cymap.ms_historial.repository.historialRepository;
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
public class historialService {

    @Autowired
    private historialRepository repo;

    @Autowired
    @Qualifier("webClientUsuarios")
    private WebClient webClientUsuarios;

    @Autowired
    @Qualifier("webClientRutas")
    private WebClient webClientRutas;

    @CircuitBreaker(name = "usuariosCB", fallbackMethod = "fallbackRegistrar")
    public Mono<historialModel> registrar(historialModel h) {

        System.out.println("=== INICIANDO VALIDACIÓN REACTIVA EN HISTORIAL ===");
        System.out.println("Validando Usuario ID: " + h.getUsuarioId());
        System.out.println("Validando Ruta ID: " + h.getRutaId());

        Mono<Boolean> monoUsuario = webClientUsuarios.get()
                .uri("/{id}", h.getUsuarioId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    System.out.println("[ERROR 4xx] El servicio de usuarios reportó que el ID no existe.");
                    return Mono.error(new RuntimeException("El usuario con ID " + h.getUsuarioId() + " no existe."));
                })
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true);

        Mono<Boolean> monoRuta = webClientRutas.get()
                .uri("/{id}", h.getRutaId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    System.out.println("[ERROR 4xx] El servicio de rutas reportó que el ID no existe.");
                    return Mono.error(new RuntimeException("La ruta con ID " + h.getRutaId() + " no existe."));
                })
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true);

        return Mono.zip(monoUsuario, monoRuta)
                .flatMap(tuple -> {
                    Boolean existeUsuario = tuple.getT1();
                    Boolean existeRuta = tuple.getT2();

                    if (Boolean.TRUE.equals(existeUsuario) && Boolean.TRUE.equals(existeRuta)) {
                        System.out.println("Validaciones exitosas. Guardando en la base de datos de historial...");
                        return Mono.just(repo.save(h));
                    }
                    return Mono.error(new RuntimeException("No se pudo registrar el historial por inconsistencia en los datos."));
                })
                .onErrorMap(throwable -> {
                    return new RuntimeException(throwable.getMessage());
                });
    }

    public Mono<historialModel> fallbackRegistrar(historialModel h, Throwable t) {
        System.err.println("!!! FALLBACK ACTIVADO EN HISTORIAL !!!");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }

        return Mono.error(new RuntimeException("Fallo en el circuito de comunicación. Detalle: " + t.toString()));
    }

    public List<historialModel> listar() {
        return repo.findAll();
    }

    public historialModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<historialModel> obtenerPorUsuarioId(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public historialModel actualizar(Long id, historialModel nuevosDatos) {
        Optional<historialModel> historialExistente = repo.findById(id);

        if (historialExistente.isPresent()) {
            historialModel h = historialExistente.get();
            h.setUsuarioId(nuevosDatos.getUsuarioId());
            h.setRutaId(nuevosDatos.getRutaId());
            h.setFechaRealizada(nuevosDatos.getFechaRealizada());
            h.setTiempoMinutos(nuevosDatos.getTiempoMinutos());
            return repo.save(h);
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