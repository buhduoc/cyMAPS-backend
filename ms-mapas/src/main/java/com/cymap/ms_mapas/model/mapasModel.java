package com.cymap.ms_mapas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mapas")
@Data
public class mapasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCapa;

    @Column(nullable = false)
    private String proveedor;

    @Column(nullable = false)
    private boolean activo;
}
