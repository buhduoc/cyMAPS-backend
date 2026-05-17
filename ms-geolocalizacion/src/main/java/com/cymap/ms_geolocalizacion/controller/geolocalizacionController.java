package com.cymap.ms_geolocalizacion.controller;

import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import com.cymap.ms_geolocalizacion.service.geolocalizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geolocalizacion")
public class geolocalizacionController {

    @Autowired
    private geolocalizacionService service;

    @PostMapping
    public geolocalizacionModel crear(@RequestBody geolocalizacionModel g) {
        return service.registrar(g);
    }

    @GetMapping
    public List<geolocalizacionModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public geolocalizacionModel buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<geolocalizacionModel> buscarUltimaUbicacion(@PathVariable Long usuarioId) {
        geolocalizacionModel ubi = service.obtenerUltimaUbicacion(usuarioId);
        if (ubi != null) {
            return ResponseEntity.ok(ubi);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<geolocalizacionModel> actualizar(@PathVariable Long id, @RequestBody geolocalizacionModel g) {
        geolocalizacionModel actualizado = service.actualizar(id, g);
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