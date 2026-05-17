package com.cymap.ms_comunidad.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comunidades")
@Data
public class comunidadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreGrupo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Long creadorId;
}