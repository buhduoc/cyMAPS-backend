CREATE TABLE disponibilidades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ruta_id BIGINT NOT NULL UNIQUE,
    estado VARCHAR(255) NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    ultima_actualizacion VARCHAR(255) NOT NULL
);