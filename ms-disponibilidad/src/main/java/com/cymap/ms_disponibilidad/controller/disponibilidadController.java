package com.cymap.ms_disponibilidad.controller;

import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import com.cymap.ms_disponibilidad.service.disponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class disponibilidadController {

    @Autowired
    private disponibilidadService service;

    @PostMapping
    public disponibilidadModel crear(@RequestBody disponibilidadModel d) {
        return service.registrar(d);
    }

    @GetMapping
    public List<disponibilidadModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public disponibilidadModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
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