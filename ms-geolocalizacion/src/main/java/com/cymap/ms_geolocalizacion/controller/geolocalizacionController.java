package com.cymap.ms_geolocalizacion.controller;

import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import com.cymap.ms_geolocalizacion.service.geolocalizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/geolocalizacion")
public class geolocalizacionController {

    @Autowired
    private geolocalizacionService service;

    @PostMapping
    public Mono<ResponseEntity<Object>> crear(@RequestBody geolocalizacionModel g) {
        return service.registrarReactivo(g)
                .map(guardado -> ResponseEntity.ok((Object) guardado))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body((Object) e.getMessage())
                ));
    }

    @GetMapping
    public ResponseEntity<List<geolocalizacionModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<geolocalizacionModel> buscarPorId(@PathVariable Long id) {
        geolocalizacionModel encontrado = service.obtenerPorId(id);
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<geolocalizacionModel> buscarUltimaUbicacion(@PathVariable Long usuarioId) {
        geolocalizacionModel ubi = service.obtenerUltimaUbicacion(usuarioId);
        if (ubi != null) {
            return ResponseEntity.ok(ubi);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<geolocalizacionModel> actualizar(@PathVariable Long id, @RequestBody geolocalizacionModel g) {
        geolocalizacionModel actualizado = service.actualizar(id, g);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}