package com.cymap.ms_historial.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "historiales")
@Data
public class historialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Long rutaId;

    @Column(nullable = false)
    private LocalDate fechaRealizada;

    @Column(nullable = false)
    private int tiempoMinutos;
}