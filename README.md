# cyMAPS - backend

Proyecto Fullstack 2026 - DUOC UC 

## Miembros

* **Nicolas Oyarzo**
* **Alexander Gutierrrez**
* **Lucas Alarcon**

## Versiones

| Version | Funcional/NoFuncional | Fecha |
| ------------ | ------------ | --------- |
| Version 1.0    | Funcional  | 18/05/2026 |
| Version 1.1 | "Funcional" | 19/05/2026 |
| Version 1.2 | "Funcional" | 26/05/2026 |

## Documentacion de Versiones

* **Version 1.0**
* Version base del programa, funcional con laragon, no funcional entre microservicios.
--------------------------------------
  | Microservicios | Port |
  | ------------ | ------------ |
  | ms-comentarios | 8089 |
  | ms-comunidad | 8088 |
  | ms-disponibilidad | 8087 |
  | ms-geolocalizacion | 8086 |
  | ms-historial | 8085 |
  | ms-imagenes | 8084 |
  | ms-mapas | 8083 |
  | ms-personalizacion | 8082 |
  | ms-rutas | 8081 |
  | ms-usuarios | 8080 |

--------------------------------------

* **Version 1.1**
* Version 1.1 del programa, funcional con laragon, ahora funciona entre microservicios (esto fue probado en pc local),
las conexiones son las siguientes:
--------------------------------------
| Microservicios | Funcional/NoFuncional |
| ------------ | ------------ |
| ms-usuario + ms-rutas + ms-comentarios | Funcional |
| ms-usuarios + ms-personalizacion | Funcional |
| ms-ruta + ms-imagenes | Funcional |
| ms-usuarios + ms-ruta + ms-historial | Funcional |
| ms-usuarios + ms-geolocalizaciones | Funcional |
| ms-ruta + ms-disponibilidades | Funcional |
| ms-usuarios + ms-comunidad  | Funcional |
--------------------------------------
**RECORDATORIO: ESTO FUE PROBADO EN UN PC LOCAL, ESTO PUEDE CONTENER ERRORES EN OTROS COMPUTADORES DE MOMENTO**

--------------------------------------

* **Version 1.2**
* Version 1.2 del programa, todo funcional en pc local, actualizacion y arreglo a algunos microservicios.
--------------------------------------
| Microservicios | Funcional/NoFuncional |
| ------------ | ------------ |
| ms-usuario + ms-rutas + ms-comentarios | Funcional |
| ms-usuarios + ms-personalizacion | Funcional |
| ms-ruta + ms-imagenes | Funcional |
| ms-usuarios + ms-ruta + ms-historial | Funcional |
| ms-usuarios + ms-geolocalizaciones | Funcional |
| ms-ruta + ms-disponibilidades | Funcional |
| ms-usuarios + ms-comunidad  | Funcional |
--------------------------------------
**RECORDATORIO: ESTO FUE PROBADO EN UN PC LOCAL, ESTO PUEDE CONTENER ERRORES EN OTROS COMPUTADORES DE MOMENTO**

--------------------------------------

## Funcionamiento Microservicios
* Todos los http de los Microservicios.

* **ms-comentarios**
* GET http://localhost:8089/api/comentarios
* POST http://localhost:8089/api/comentarios
* GET http://localhost:8089/api/comentarios/{id}
* GET http://localhost:8089/api/comentarios/ruta/{rutaId}
* PUT http://localhost:8089/api/comentarios/{id}
* DELETE http://localhost:8089/api/comentarios/{id}

* **ms-comunidad**
* GET http://localhost:8088/api/comunidad
* POST http://localhost:8088/api/comunidad
* GET http://localhost:8088/api/comunidad/{id}
* GET http://localhost:8088/api/comunidad/usuario/{usuarioId}
* PUT http://localhost:8088/api/comunidad/{id}
* DELETE http://localhost:8088/api/comunidad/{id}

* **ms-disponibilidad**
* GET http://localhost:8087/api/disponibilidad
* GET http://localhost:8087/api/disponibilidad/ruta/{rutaId}
* POST http://localhost:8087/api/disponibilidad
* GET http://localhost:8087/api/disponibilidad/{id}
* PUT http://localhost:8087/api/disponibilidad/{id}
* DELETE http://localhost:8087/api/disponibilidad/{id}

*  **ms-geolocalizacion**
*  GET http://localhost:8086/api/geolocalizacion
*  GET http://localhost:8086/api/geolocalizacion/usuario/{usuarioId}
*  POST http://localhost:8086/api/geolocalizacion
*  PUT http://localhost:8086/api/geolocalizacion/{id}
*  DELETE http://localhost:8086/api/geolocalizacion/{id}

*   **ms-historial**
*   GET http://localhost:8085/api/historial
*   POST http://localhost:8085/api/historial
*   GET http://localhost:8085/api/historial/usuario/{usuarioId}
*   GET http://localhost:8085/api/historial/{id}
*   PUT http://localhost:8085/api/historial/{id}
*   DELETE http://localhost:8085/api/historial/{id}

*    **ms-imagenes**
*    GET http://localhost:8084/api/imagenes
*    POST http://localhost:8084/api/imagenes
*    GET http://localhost:8084/api/imagenes/ruta/{id}
*    GET http://localhost:8084/api/imagenes/{id}
*    PUT http://localhost:8084/api/imagenes/ruta/{id}
*    DELETE http://localhost:8084/api/imagenes/{id}

*    **ms-mapas**
*    GET http://localhost:8083/api/mapas
*    POST http://localhost:8083/api/mapas
*    GET http://localhost:8083/api/mapas/{id}
*    PUT http://localhost:8083/api/mapas/{id}
*    DELETE http://localhost:8083/api/mapas/{id}

*    **ms-personalizacion**
*    GET http://localhost:8082/api/personalizacion
*    POST http://localhost:8082/api/personalizacion
*    GET http://localhost:8082/api/personalizacion/{id}
*    GET http://localhost:8082/api/personalizacion/usuario/{usuarioId}
*    PUT http://localhost:8082/api/personalizacion/{id}
*    DELETE http://localhost:8082/api/personalizacion/{id}

*    **ms-rutas**
*    GET http://localhost:8081/rutas
*    POST http://localhost:8081/rutas
*    GET http://localhost:8081/rutas/{id}
*    PUT http://localhost:8081/rutas/{id}
*    DELETE http://localhost:8081/rutas/{id}

*    **ms-usuario**
*    GET http://localhost:8080/api/usuarios
*    GET http://localhost:8080/api/usuarios/{id}
*    POST http://localhost:8080/api/usuarios
*    PUT http://localhost:8080/api/usuarios/{id}
*    DELETE http://localhost:8080/api/usuarios/{id}
