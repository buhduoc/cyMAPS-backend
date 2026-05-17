package com.cymap.ms_geolocalizacion.repository;

import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface geolocalizacionRepository extends JpaRepository<geolocalizacionModel, Long> {
    /*
     Permite encontrar la última ubicación de un ciclista
     */
    Optional<geolocalizacionModel> findTopByUsuarioIdOrderByIdDesc(Long usuarioId);
}