package com.cymap.ms_historial.repository;

import com.cymap.ms_historial.model.historialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface historialRepository extends JpaRepository<historialModel, Long> {
    /*
     obtener historial del ciclista
     */
    List<historialModel> findByUsuarioId(Long usuarioId);
}