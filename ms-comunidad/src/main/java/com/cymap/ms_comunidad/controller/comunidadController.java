package com.cymap.ms_comunidad.controller;

import com.cymap.ms_comunidad.model.comunidadModel;
import com.cymap.ms_comunidad.service.comunidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunidad")
public class comunidadController {

    @Autowired
    private comunidadService service;

    @PostMapping
    public comunidadModel crear(@RequestBody comunidadModel c) {
        return service.registrar(c);
    }

    @GetMapping
    public List<comunidadModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public comunidadModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    // Endpoint para ver qué grupos maneja un usuario en concreto: /api/comunidad/creador/1
    @GetMapping("/creador/{creadorId}")
    public List<comunidadModel> buscarPorCreadorId(@PathVariable Long creadorId) {
        return service.obtenerPorCreadorId(creadorId);
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