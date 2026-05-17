package com.cymap.ms_mapas.repository;

import com.cymap.ms_mapas.model.mapasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface mapasRepository extends JpaRepository<mapasModel, Long> {
}