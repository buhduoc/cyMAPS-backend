package com.cymap.ms_imagenes.service;

import com.cymap.ms_imagenes.model.imagenesModel;
import com.cymap.ms_imagenes.repository.imagenesRepository;
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
public class imagenesService {

    @Autowired
    private imagenesRepository repo;

    @Autowired
    @Qualifier("webClientRutas")
    private WebClient webClientRutas;

    @CircuitBreaker(name = "imagenesCB", fallbackMethod = "fallbackRegistrar")
    public Mono<imagenesModel> registrarReactivo(imagenesModel i) {
        System.out.println("=== VALIDANDO RUTA EN MS-IMAGENES ===");
        System.out.println("Validando Ruta ID: " + i.getRutaId());

        return webClientRutas.get()
                .uri("/{id}", i.getRutaId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("La ruta con ID " + i.getRutaId() + " no existe en el sistema."))
                )
                .bodyToMono(Void.class)
                .map(obj -> true)
                .defaultIfEmpty(true)
                .flatMap(existe -> {
                    System.out.println("Ruta verificada con éxito. Guardando imagen...");
                    imagenesModel guardada = repo.save(i);
                    return Mono.just(guardada);
                });
    }

    public Mono<imagenesModel> fallbackRegistrar(imagenesModel i, Throwable t) {
        System.err.println("FALLBACK ACTIVADO");
        System.err.println("Causa real: " + t.getMessage());

        if (t.getMessage() != null && t.getMessage().contains("no existe")) {
            return Mono.error(new RuntimeException(t.getMessage()));
        }
        return Mono.error(new RuntimeException("El servicio de verificación de rutas no está disponible en este momento."));
    }

    public imagenesModel registrar(imagenesModel i) {
        return repo.save(i);
    }

    public List<imagenesModel> listar() {
        return repo.findAll();
    }

    public imagenesModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<imagenesModel> obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId);
    }

    public imagenesModel actualizar(Long id, imagenesModel nuevosDatos) {
        Optional<imagenesModel> imagenExistente = repo.findById(id);

        if (imagenExistente.isPresent()) {
            imagenesModel i = imagenExistente.get();
            i.setRutaId(nuevosDatos.getRutaId());
            i.setUrlStorage(nuevosDatos.getUrlStorage());
            i.setPesoKb(nuevosDatos.getPesoKb());
            return repo.save(i);
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