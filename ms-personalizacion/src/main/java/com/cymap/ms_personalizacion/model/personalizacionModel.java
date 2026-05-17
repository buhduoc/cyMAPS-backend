package com.cymap.ms_personalizacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "personalizaciones")
@Data
public class personalizacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long usuarioId;

    @Column(nullable = false, length = 7)
    private String colorRutaHex;

    @Column(nullable = false)
    private boolean modoOscuro;

    @Column(nullable = true)
    private String vistaMapaDefecto;
}