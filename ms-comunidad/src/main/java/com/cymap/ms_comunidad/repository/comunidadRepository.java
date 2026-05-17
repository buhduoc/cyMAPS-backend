package com.cymap.ms_comunidad.repository;

import com.cymap.ms_comunidad.model.comunidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface comunidadRepository extends JpaRepository<comunidadModel, Long> {
    /*
     Permite buscar todos los grupos
     */
    List<comunidadModel> findByCreadorId(Long creadorId);
}