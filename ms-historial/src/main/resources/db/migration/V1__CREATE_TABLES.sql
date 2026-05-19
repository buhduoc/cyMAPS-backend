CREATE TABLE historial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    ruta_id BIGINT NOT NULL,
    fecha_realizada VARCHAR(255) NOT NULL,
    tiempo_minutos INT NOT NULL
);