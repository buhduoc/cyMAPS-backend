package com.cymap.ms_rutas.repository;

import com.cymap.ms_rutas.model.rutasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface rutasRepository extends JpaRepository<rutasModel, Long> {
}
