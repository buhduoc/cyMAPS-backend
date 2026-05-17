package com.cymap.ms_geolocalizacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "geolocalizaciones")
@Data
public class geolocalizacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;
}