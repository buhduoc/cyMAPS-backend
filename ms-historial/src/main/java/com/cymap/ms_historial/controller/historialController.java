package com.cymap.ms_historial.controller;

import com.cymap.ms_historial.model.historialModel;
import com.cymap.ms_historial.service.historialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class historialController {

    @Autowired
    private historialService service;

    @PostMapping
    public historialModel crear(@RequestBody historialModel h) {
        return service.registrar(h);
    }

    @GetMapping
    public List<historialModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public historialModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<historialModel> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        return service.obtenerPorUsuarioId(usuarioId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<historialModel> actualizar(@PathVariable Long id, @RequestBody historialModel h) {
        historialModel actualizado = service.actualizar(id, h);
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