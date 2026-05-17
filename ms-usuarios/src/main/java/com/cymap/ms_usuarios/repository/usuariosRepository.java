package com.cymap.ms_usuarios.repository;

import com.cymap.ms_usuarios.model.usuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuariosRepository  extends JpaRepository<usuariosModel, Long> {
}
