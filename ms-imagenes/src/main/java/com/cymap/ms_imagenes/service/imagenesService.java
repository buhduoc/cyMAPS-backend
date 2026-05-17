package com.cymap.ms_imagenes.service;

import com.cymap.ms_imagenes.model.imagenesModel;
import com.cymap.ms_imagenes.repository.imagenesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class imagenesService {

    @Autowired
    private imagenesRepository repo;

    public imagenesModel registrar(imagenesModel i) {
        return repo.save(i);
    }

    public List<imagenesModel> listar() {
        return repo.findAll();
    }

    public imagenesModel obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<imagenesModel> obtenerPorRutaId(Long rutaId) {
        return repo.findByRutaId(rutaId);
    }

    public imagenesModel actualizar(Long id, imagenesModel nuevosDatos) {
        Optional<imagenesModel> imagenExistente = repo.findById(id);

        if (imagenExistente.isPresent()) {
            imagenesModel i = imagenExistente.get();
            i.setRutaId(nuevosDatos.getRutaId());
            i.setUrlStorage(nuevosDatos.getUrlStorage());
            i.setPesoKb(nuevosDatos.getPesoKb());
            return repo.save(i);
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