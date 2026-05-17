CREATE TABLE personalizaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    color_ruta_hex VARCHAR(7) NOT NULL,
    modo_oscuro TINYINT(1) NOT NULL,
    vista_mapa_defecto VARCHAR(255) NULL
);