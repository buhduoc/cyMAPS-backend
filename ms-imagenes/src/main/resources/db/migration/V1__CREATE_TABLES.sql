CREATE TABLE imagenes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ruta_id BIGINT NOT NULL,
    url_storage VARCHAR(255) NOT NULL,
    peso_kb INT NOT NULL
);