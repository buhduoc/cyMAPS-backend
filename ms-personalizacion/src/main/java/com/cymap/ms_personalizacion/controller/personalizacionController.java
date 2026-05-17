package com.cymap.ms_personalizacion.controller;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.service.personalizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personalizacion")
public class personalizacionController {

    @Autowired
    private personalizacionService service;

    @PostMapping
    public personalizacionModel crear(@RequestBody personalizacionModel p) {
        return service.registrar(p);
    }

    @GetMapping
    public List<personalizacionModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public personalizacionModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
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