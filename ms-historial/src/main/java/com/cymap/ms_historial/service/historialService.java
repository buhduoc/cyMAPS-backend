package com.cymap.ms_historial.service;

import com.cymap.ms_historial.model.historialModel;
import com.cymap.ms_historial.repository.historialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class historialService {

    @Autowired
    private historialRepository repo;

    public historialModel registrar(historialModel h) {
        return repo.save(h);
    }

    public List<historialModel> listar() {
        return repo.findAll();
    }

    public historialModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<historialModel> obtenerPorUsuarioId(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public historialModel actualizar(Long id, historialModel nuevosDatos) {
        Optional<historialModel> historialExistente = repo.findById(id);

        if (historialExistente.isPresent()) {
            historialModel h = historialExistente.get();
            h.setUsuarioId(nuevosDatos.getUsuarioId());
            h.setRutaId(nuevosDatos.getRutaId());
            h.setFechaRealizada(nuevosDatos.getFechaRealizada());
            h.setTiempoMinutos(nuevosDatos.getTiempoMinutos());
            return repo.save(h);
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