CREATE TABLE historiales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    ruta_id BIGINT NOT NULL,
    fecha_realizada DATE NOT NULL,
    tiempo_minutos INT NOT NULL
);