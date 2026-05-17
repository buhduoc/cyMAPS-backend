package com.cymap.ms_imagenes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "imagenes")
@Data
public class imagenesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rutaId;

    @Column(nullable = false)
    private String urlStorage;

    @Column(nullable = false)
    private int pesoKb;
}