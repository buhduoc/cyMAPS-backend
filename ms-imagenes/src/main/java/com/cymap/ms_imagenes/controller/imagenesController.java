package com.cymap.ms_imagenes.controller;

import com.cymap.ms_imagenes.model.imagenesModel;
import com.cymap.ms_imagenes.service.imagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
public class imagenesController {

    @Autowired
    private imagenesService service;

    @PostMapping
    public Mono<ResponseEntity<Object>> crear(@RequestBody imagenesModel i) {
        return service.registrarReactivo(i)
                .map(guardado -> ResponseEntity.ok((Object) guardado))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body((Object) e.getMessage())
                ));
    }

    @GetMapping
    public ResponseEntity<List<imagenesModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<imagenesModel> buscarPorId(@PathVariable Long id) {
        imagenesModel encontrado = service.obtenerPorId(id);
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<imagenesModel>> buscarPorRutaId(@PathVariable Long rutaId) {
        return ResponseEntity.ok(service.obtenerPorRutaId(rutaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<imagenesModel> actualizar(@PathVariable Long id, @RequestBody imagenesModel i) {
        imagenesModel actualizado = service.actualizar(id, i);
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