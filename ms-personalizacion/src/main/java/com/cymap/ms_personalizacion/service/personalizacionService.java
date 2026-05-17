package com.cymap.ms_personalizacion.service;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.repository.personalizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class personalizacionService {

    @Autowired
    private personalizacionRepository repo;

    public personalizacionModel registrar(personalizacionModel p) {
        return repo.save(p);
    }

    public List<personalizacionModel> listar() {
        return repo.findAll();
    }

    public personalizacionModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public personalizacionModel obtenerPorUsuarioId(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public personalizacionModel actualizar(Long id, personalizacionModel nuevasPreferencias) {
        Optional<personalizacionModel> configExistente = repo.findById(id);

        if (configExistente.isPresent()) {
            personalizacionModel p = configExistente.get();

            p.setUsuarioId(nuevasPreferencias.getUsuarioId());
            p.setColorRutaHex(nuevasPreferencias.getColorRutaHex());
            p.setModoOscuro(nuevasPreferencias.isModoOscuro());
            p.setVistaMapaDefecto(nuevasPreferencias.getVistaMapaDefecto());
            return repo.save(p);
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