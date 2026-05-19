package com.cymap.ms_disponibilidad.service;

import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import com.cymap.ms_disponibilidad.repository.disponibilidadRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class disponibilidadService {

    @Autowired
    private disponibilidadRepository repo;

    @Autowired
    @Qualifier("webClientRutas")
    private WebClient webClientRutas;

    @CircuitBreaker(name = "disponibilidadCB", fallbackMethod = "fallbackRegistrar")
    public Mono<disponibilidadModel> registrarReactivo(disponibilidadModel d) {
        System.out.println("VALIDANDO RUTA");
        System.out.println("Validando Ruta ID: " + d.getRutaId());

        return webClientRutas.get()
                .uri("/{id}", d.getRutaId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("La ruta con ID " + d.getRutaId() + " no existe en el sistema."))
                )
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true)
                .flatMap(existe -> {
                    System.out.println("Ruta verificada con éxito. Procesando datos...");

                    Optional<disponibilidadModel> dispExistente = repo.findByRutaId(d.getRutaId());

                    disponibilidadModel entidadFinal;

                    if (dispExistente.isPresent()) {
                        entidadFinal = dispExistente.get();
                        entidadFinal.setEstado(d.getEstado());
                        entidadFinal.setMotivo(d.getMotivo());
                    } else {
                        entidadFinal = d;
                    }

                    entidadFinal.setUltimaActualizacion(entidadFinal.getUltimaActualizacion());

                    disponibilidadModel guardado = repo.save(entidadFinal);
                    return Mono.just(guardado);
                });
    }

    public Mono<disponibilidadModel> fallbackRegistrar(disponibilidadModel d, Throwable t) {
        System.err.println("!!! FALLBACK ACTIVADO EN MS-DISPONIBILIDAD !!!");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }
        return Mono.error(new RuntimeException("El servicio de verificación de rutas no está disponible en este momento."));
    }

    public disponibilidadModel registrar(disponibilidadModel d) {
        d.setUltimaActualizacion(d.getUltimaActualizacion());
        return repo.save(d);
    }

    public List<disponibilidadModel> listar() {
        return repo.findAll();
    }

    public disponibilidadModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public disponibilidadModel obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId).orElse(null);
    }

    public disponibilidadModel actualizar(Long id, disponibilidadModel nuevosDatos) {
        Optional<disponibilidadModel> dispExistente = repo.findById(id);

        if (dispExistente.isPresent()) {
            disponibilidadModel d = dispExistente.get();
            d.setRutaId(nuevosDatos.getRutaId());
            d.setEstado(nuevosDatos.getEstado());
            d.setMotivo(nuevosDatos.getMotivo());
            d.setUltimaActualizacion(nuevosDatos.getUltimaActualizacion());
            return repo.save(d);
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