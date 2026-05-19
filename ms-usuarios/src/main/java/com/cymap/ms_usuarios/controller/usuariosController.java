package com.cymap.ms_usuarios.controller;

import com.cymap.ms_usuarios.model.usuariosModel;
import com.cymap.ms_usuarios.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class usuariosController {

    @Autowired
    private usuarioService service;

    @PostMapping
    public usuariosModel registrar(@RequestBody usuariosModel u) {
        return service.registrar(u);
    }

    @GetMapping
    public List<usuariosModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuariosModel> obtenerPorId(@PathVariable Long id) {
        usuariosModel usuario = service.obtenerPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<usuariosModel> actualizar(@PathVariable Long id, @RequestBody usuariosModel u) {
        usuariosModel actualizado = service.actualizar(id, u);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}