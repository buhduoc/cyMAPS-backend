package com.cymap.ms_rutas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rutas")
@Data
public class rutasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Long usuarioId;
}
