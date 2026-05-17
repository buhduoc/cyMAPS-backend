package com.cymap.ms_comentarios.repository;

import com.cymap.ms_comentarios.model.comentariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface comentariosRepository extends JpaRepository<comentariosModel, Long> {
    /*
     Permite obtener todos los comentarios asociados a una ruta
     */
    List<comentariosModel> findByRutaId(Long rutaId);
}