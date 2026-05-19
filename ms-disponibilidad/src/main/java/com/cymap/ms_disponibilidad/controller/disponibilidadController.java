package com.cymap.ms_disponibilidad.controller;

import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import com.cymap.ms_disponibilidad.service.disponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class disponibilidadController {

    @Autowired
    private disponibilidadService service;

    @PostMapping
    public Mono<ResponseEntity<Object>> crear(@RequestBody disponibilidadModel d) {
        return service.registrarReactivo(d)
                .map(guardado -> ResponseEntity.ok((Object) guardado))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body((Object) e.getMessage())
                ));
    }

    @GetMapping
    public ResponseEntity<List<disponibilidadModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<disponibilidadModel> buscarPorId(@PathVariable Long id) {
        disponibilidadModel encontrado = service.obtenerPorId(id);
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<disponibilidadModel> buscarPorRutaId(@PathVariable Long rutaId) {
        disponibilidadModel disp = service.obtenerPorRutaId(rutaId);
        if (disp != null) {
            return ResponseEntity.ok(disp);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<disponibilidadModel> actualizar(@PathVariable Long id, @RequestBody disponibilidadModel d) {
        disponibilidadModel actualizado = service.actualizar(id, d);
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