package com.cymap.ms_comunidad.controller;

import com.cymap.ms_comunidad.model.comunidadModel;
import com.cymap.ms_comunidad.service.comunidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/comunidad")
public class comunidadController {

    @Autowired
    private comunidadService service;

    @PostMapping
    public Mono<ResponseEntity<Object>> crear(@RequestBody comunidadModel c) {
        return service.registrarReactivo(c)
                .map(guardado -> ResponseEntity.ok((Object) guardado))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body((Object) e.getMessage())
                ));
    }

    @GetMapping
    public ResponseEntity<List<comunidadModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<comunidadModel> buscarPorId(@PathVariable Long id) {
        comunidadModel encontrado = service.obtenerPorId(id);
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/creador/{creadorId}")
    public ResponseEntity<List<comunidadModel>> buscarPorCreadorId(@PathVariable Long creadorId) {
        return ResponseEntity.ok(service.obtenerPorCreadorId(creadorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<comunidadModel> actualizar(@PathVariable Long id, @RequestBody comunidadModel c) {
        comunidadModel actualizado = service.actualizar(id, c);
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