package com.cymap.ms_comunidad.service;

import com.cymap.ms_comunidad.model.comunidadModel;
import com.cymap.ms_comunidad.repository.comunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class comunidadService {

    @Autowired
    private comunidadRepository repo;

    public comunidadModel registrar(comunidadModel c) {
        return repo.save(c);
    }

    public List<comunidadModel> listar() {
        return repo.findAll();
    }

    public comunidadModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<comunidadModel> obtenerPorCreadorId(Long creadorId) {
        return repo.findByCreadorId(creadorId);
    }

    public comunidadModel actualizar(Long id, comunidadModel nuevosDatos) {
        Optional<comunidadModel> comunidadExistente = repo.findById(id);

        if (comunidadExistente.isPresent()) {
            comunidadModel c = comunidadExistente.get();
            c.setNombreGrupo(nuevosDatos.getNombreGrupo());
            c.setDescripcion(nuevosDatos.getDescripcion());
            c.setCreadorId(nuevosDatos.getCreadorId());
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