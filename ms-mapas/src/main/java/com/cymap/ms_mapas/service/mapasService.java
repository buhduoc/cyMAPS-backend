package com.cymap.ms_mapas.service;

import com.cymap.ms_mapas.model.mapasModel;
import com.cymap.ms_mapas.repository.mapasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class mapasService {

    @Autowired
    private mapasRepository repo;

    public mapasModel registrar(mapasModel m) {
        return repo.save(m);
    }

    public List<mapasModel> listar() {
        return repo.findAll();
    }

    public mapasModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public mapasModel actualizar(Long id, mapasModel nuevosDatos) {
        Optional<mapasModel> mapaExistente = repo.findById(id);

        if (mapaExistente.isPresent()) {
            mapasModel m = mapaExistente.get();
            m.setNombreCapa(nuevosDatos.getNombreCapa());
            m.setProveedor(nuevosDatos.getProveedor());
            m.setActivo(nuevosDatos.isActivo());
            return repo.save(m);
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