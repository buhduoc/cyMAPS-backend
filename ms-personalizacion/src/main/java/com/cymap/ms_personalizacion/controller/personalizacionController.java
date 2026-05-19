package com.cymap.ms_personalizacion.controller;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.service.personalizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/personalizacion")
public class personalizacionController {

    @Autowired
    private personalizacionService service;

    @PostMapping
    public Mono<ResponseEntity<Object>> crear(@RequestBody personalizacionModel p) {
        return service.guardarPreferencias(p)
                .map(guardado -> ResponseEntity.ok((Object) guardado))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body((Object) e.getMessage())
                ));
    }

    @GetMapping
    public ResponseEntity<List<personalizacionModel>> listar() {
        List<personalizacionModel> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<personalizacionModel> buscarPorId(@PathVariable Long id) {
        personalizacionModel encontrado = service.obtenerPorId(id);
        if (encontrado != null) {
            return ResponseEntity.ok(encontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<personalizacionModel> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        personalizacionModel config = service.obtenerPorUsuarioId(usuarioId);
        if (config != null) {
            return ResponseEntity.ok(config);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<personalizacionModel> actualizar(@PathVariable Long id, @RequestBody personalizacionModel p) {
        personalizacionModel actualizado = service.actualizar(id, p);
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