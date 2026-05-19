CREATE TABLE comunidades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_grupo VARCHAR(255) NOT NULL UNIQUE,
    descripcion TEXT NOT NULL,
    usuario_id BIGINT NOT NULL
);