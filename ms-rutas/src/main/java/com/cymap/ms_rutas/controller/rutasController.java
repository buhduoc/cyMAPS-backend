package com.cymap.ms_rutas.controller;

import com.cymap.ms_rutas.model.rutasModel;
import com.cymap.ms_rutas.service.rutasService; // Importamos tu servicio concreto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class rutasController {

    @Autowired
    private rutasService service; // Cambiamos el repo por el servicio

    // 1. CREATE
    @PostMapping
    public rutasModel crearRuta(@RequestBody rutasModel r) {
        return service.guardarRuta(r);
    }

    // 2. READ ALL
    @GetMapping
    public List<rutasModel> listar() {
        return service.obtenerTodas();
    }

    // 3. READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<rutasModel> obtenerPorId(@PathVariable Long id) {
        rutasModel ruta = service.obtenerPorId(id);
        if (ruta != null) {
            return ResponseEntity.ok(ruta);
        }
        return ResponseEntity.notFound().build();
    }

    // 4. UPDATE (¡Faltaba!)
    @PutMapping("/{id}")
    public ResponseEntity<rutasModel> actualizar(@PathVariable Long id, @RequestBody rutasModel r) {
        rutasModel actualizada = service.actualizar(id, r);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. DELETE (¡Faltaba!)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // Estado 204 OK
        }
        return ResponseEntity.notFound().build();
    }
}
