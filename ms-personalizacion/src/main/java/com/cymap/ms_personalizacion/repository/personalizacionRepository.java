package com.cymap.ms_personalizacion.repository;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // <-- IMPORTANTE: Asegúrate de tener este import

@Repository
public interface personalizacionRepository extends JpaRepository<personalizacionModel, Long> {
    Optional<personalizacionModel> findByUsuarioId(Long usuarioId);
}