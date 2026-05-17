package com.cymap.ms_comentarios.service;

import com.cymap.ms_comentarios.model.comentariosModel;
import com.cymap.ms_comentarios.repository.comentariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class comentariosService {

    @Autowired
    private comentariosRepository repo;

    public comentariosModel registrar(comentariosModel c) {
        return repo.save(c);
    }

    public List<comentariosModel> listar() {
        return repo.findAll();
    }

    public comentariosModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<comentariosModel> obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId);
    }

    public comentariosModel actualizar(Long id, comentariosModel nuevosDatos) {
        Optional<comentariosModel> comentarioExistente = repo.findById(id);

        if (comentarioExistente.isPresent()) {
            comentariosModel c = comentarioExistente.get();
            c.setRutaId(nuevosDatos.getRutaId());
            c.setUsuarioId(nuevosDatos.getUsuarioId());
            c.setContenido(nuevosDatos.getContenido());
            return repo.save(c);
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