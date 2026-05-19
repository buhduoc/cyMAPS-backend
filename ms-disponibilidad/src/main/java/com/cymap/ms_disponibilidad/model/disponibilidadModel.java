package com.cymap.ms_disponibilidad.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "disponibilidades")
@Data
public class disponibilidadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long rutaId;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String ultimaActualizacion;
}