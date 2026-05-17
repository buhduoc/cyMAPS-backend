package com.cymap.ms_geolocalizacion.service;

import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import com.cymap.ms_geolocalizacion.repository.geolocalizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class geolocalizacionService {

    @Autowired
    private geolocalizacionRepository repo;

    public geolocalizacionModel registrar(geolocalizacionModel g) {
        return repo.save(g);
    }

    public List<geolocalizacionModel> listar() {
        return repo.findAll();
    }

    public geolocalizacionModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public geolocalizacionModel obtenerUltimaUbicacion(Long usuarioId) {
        return repo.findTopByUsuarioIdOrderByIdDesc(usuarioId).orElse(null);
    }

    public geolocalizacionModel actualizar(Long id, geolocalizacionModel nuevosDatos) {
        Optional<geolocalizacionModel> posicionExistente = repo.findById(id);

        if (posicionExistente.isPresent()) {
            geolocalizacionModel g = posicionExistente.get();
            g.setUsuarioId(nuevosDatos.getUsuarioId());
            g.setLatitud(nuevosDatos.getLatitud());
            g.setLongitud(nuevosDatos.getLongitud());
            return repo.save(g);
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