package com.cymap.ms_rutas.service;

import com.cymap.ms_rutas.model.rutasModel;
import com.cymap.ms_rutas.repository.rutasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class rutasService {

    @Autowired
    private rutasRepository repo;

    public List<rutasModel> obtenerTodas() {
        return repo.findAll();
    }

    public rutasModel guardarRuta(rutasModel ruta) {
        return repo.save(ruta);
    }

    public rutasModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public rutasModel actualizar(Long id, rutasModel nuevasRutas) {
        Optional<rutasModel> rutaExistente = repo.findById(id);

        if (rutaExistente.isPresent()) {
            rutasModel r = rutaExistente.get();
            r.setNombre(nuevasRutas.getNombre());
            r.setTipo(nuevasRutas.getTipo());
            r.setCreadorId(nuevasRutas.getCreadorId());
            return repo.save(r);
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
