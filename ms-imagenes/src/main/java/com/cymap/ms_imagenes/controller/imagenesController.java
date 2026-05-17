package com.cymap.ms_imagenes.controller;

import com.cymap.ms_imagenes.model.imagenesModel;
import com.cymap.ms_imagenes.service.imagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
public class imagenesController {

    @Autowired
    private imagenesService service;

    @PostMapping
    public imagenesModel crear(@RequestBody imagenesModel i) {
        return service.registrar(i);
    }

    @GetMapping
    public List<imagenesModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public imagenesModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/ruta/{rutaId}")
    public List<imagenesModel> buscarPorRutaId(@PathVariable Long rutaId) {
        return service.obtenerPorRutaId(rutaId);
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