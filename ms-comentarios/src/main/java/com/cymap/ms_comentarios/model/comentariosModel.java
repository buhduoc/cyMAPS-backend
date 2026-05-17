package com.cymap.ms_comentarios.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comentarios")
@Data
public class comentariosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rutaId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false, length = 500)
    private String contenido;
}