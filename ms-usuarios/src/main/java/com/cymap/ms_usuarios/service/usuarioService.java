package com.cymap.ms_usuarios.service;

import com.cymap.ms_usuarios.model.usuariosModel;
import com.cymap.ms_usuarios.repository.usuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {

    @Autowired
    private usuariosRepository repo;

    public usuariosModel registrar(usuariosModel u) {
        return repo.save(u);
    }

    public List<usuariosModel> listar() {
        return repo.findAll();
    }

    public usuariosModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public usuariosModel actualizar(Long id, usuariosModel nuevosDatos) {
        Optional<usuariosModel> usuarioExistente = repo.findById(id);

        if (usuarioExistente.isPresent()) {
            usuariosModel u = usuarioExistente.get();
            u.setNombre(nuevosDatos.getNombre());
            u.setEmail(nuevosDatos.getEmail());
            u.setPassword(nuevosDatos.getPassword());
            return repo.save(u);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
