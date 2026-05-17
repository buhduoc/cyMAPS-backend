CREATE TABLE comentarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ruta_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    contenido VARCHAR(500) NOT NULL
);