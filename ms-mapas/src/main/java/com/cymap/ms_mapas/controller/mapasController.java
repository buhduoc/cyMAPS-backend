package com.cymap.ms_mapas.controller;

import com.cymap.ms_mapas.model.mapasModel;
import com.cymap.ms_mapas.service.mapasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapas")
public class mapasController {

    @Autowired
    private mapasService service;

    @PostMapping
    public mapasModel crear(@RequestBody mapasModel m) {
        return service.registrar(m);
    }

    @GetMapping
    public List<mapasModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public mapasModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<mapasModel> actualizar(@PathVariable Long id, @RequestBody mapasModel m) {
        mapasModel actualizado = service.actualizar(id, m);
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