package com.cymap.ms_disponibilidad.service;

import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import com.cymap.ms_disponibilidad.repository.disponibilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class disponibilidadService {

    @Autowired
    private disponibilidadRepository repo;

    public disponibilidadModel registrar(disponibilidadModel d) {
        d.setUltimaActualizacion(LocalDateTime.now());
        return repo.save(d);
    }

    public List<disponibilidadModel> listar() {
        return repo.findAll();
    }

    public disponibilidadModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public disponibilidadModel obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId).orElse(null);
    }

    public disponibilidadModel actualizar(Long id, disponibilidadModel nuevosDatos) {
        Optional<disponibilidadModel> dispExistente = repo.findById(id);

        if (dispExistente.isPresent()) {
            disponibilidadModel d = dispExistente.get();
            d.setRutaId(nuevosDatos.getRutaId());
            d.setEstado(nuevosDatos.getEstado());
            d.setMotivo(nuevosDatos.getMotivo());
            d.setUltimaActualizacion(LocalDateTime.now());
            return repo.save(d);
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