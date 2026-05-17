CREATE TABLE geolocalizaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    latitud DOUBLE NOT NULL,
    longitud DOUBLE NOT NULL
);