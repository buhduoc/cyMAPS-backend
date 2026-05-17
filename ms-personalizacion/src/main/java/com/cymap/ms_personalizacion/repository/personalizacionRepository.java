package com.cymap.ms_personalizacion.repository;

import com.cymap.ms_personalizacion.model.personalizacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface personalizacionRepository extends JpaRepository<personalizacionModel, Long> {
    /*
    Este permite buscar la configuracion exasta de un usuario
     */
    personalizacionModel findByUsuarioId(Long usuarioId);
}
