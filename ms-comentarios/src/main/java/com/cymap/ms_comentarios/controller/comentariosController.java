package com.cymap.ms_comentarios.controller;

import com.cymap.ms_comentarios.model.comentariosModel;
import com.cymap.ms_comentarios.service.comentariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class comentariosController {

    @Autowired
    private comentariosService service;

    @PostMapping
    public comentariosModel crear(@RequestBody comentariosModel c) {
        return service.registrar(c);
    }

    @GetMapping
    public List<comentariosModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public comentariosModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/ruta/{rutaId}")
    public List<comentariosModel> buscarPorRutaId(@PathVariable Long rutaId) {
        return service.obtenerPorRutaId(rutaId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<comentariosModel> actualizar(@PathVariable Long id, @RequestBody comentariosModel c) {
        comentariosModel actualizado = service.actualizar(id, c);
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