package com.cymap.ms_disponibilidad.repository;

import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface disponibilidadRepository extends JpaRepository<disponibilidadModel, Long> {
    /*
     Permite consultar el estado de disponibilidad
     */
    Optional<disponibilidadModel> findByRutaId(Long rutaId);
}