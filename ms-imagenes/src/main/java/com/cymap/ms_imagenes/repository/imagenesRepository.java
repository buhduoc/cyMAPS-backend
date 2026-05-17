package com.cymap.ms_imagenes.repository;

import com.cymap.ms_imagenes.model.imagenesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface imagenesRepository extends JpaRepository<imagenesModel, Long> {
    /*
    permite que cada imagen este vinculada a un id (ruta especifico)
     */
    List<imagenesModel> findByRutaId(Long rutaId);
}