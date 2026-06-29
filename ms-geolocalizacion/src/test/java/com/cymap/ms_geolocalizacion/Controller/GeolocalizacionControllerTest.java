package com.cymap.ms_geolocalizacion.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import reactor.core.publisher.Mono;

import com.cymap.ms_geolocalizacion.controller.geolocalizacionController;
import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;
import com.cymap.ms_geolocalizacion.service.geolocalizacionService;

public class GeolocalizacionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private geolocalizacionService geolocalizacionService;

    @InjectMocks
    private geolocalizacionController geolocalizacionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(geolocalizacionController).build();
    }

    @Test
    @DisplayName("GET /api/geolocalizacion/{id} -> Retorna 200 y JSON si existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarGeolocalizacion() throws Exception {

        geolocalizacionModel geo = new geolocalizacionModel();
        geo.setId(1L);
        geo.setUsuarioId(10L);
        geo.setLatitud(-33.4489);
        geo.setLongitud(-70.6693);

        when(geolocalizacionService.obtenerPorId(1L)).thenReturn(geo);

        mockMvc.perform(get("/api/geolocalizacion/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(10))
                .andExpect(jsonPath("$.latitud").value(-33.4489))
                .andExpect(jsonPath("$.longitud").value(-70.6693));
    }

    @Test
    @DisplayName("GET /api/geolocalizacion/{id} -> Retorna 404 si no existe")
    public void buscarPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(geolocalizacionService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/geolocalizacion/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/geolocalizacion -> Retorna 200 y lista JSON")
    public void listar_DeberiaRetornarLista() throws Exception {

        geolocalizacionModel geo = new geolocalizacionModel();
        geo.setId(1L);
        geo.setUsuarioId(10L);
        geo.setLatitud(-33.4489);
        geo.setLongitud(-70.6693);

        when(geolocalizacionService.listar()).thenReturn(List.of(geo));

        mockMvc.perform(get("/api/geolocalizacion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(10))
                .andExpect(jsonPath("$[0].latitud").value(-33.4489))
                .andExpect(jsonPath("$[0].longitud").value(-70.6693));
    }

    @Test
    @DisplayName("POST /api/geolocalizacion -> Retorna 200 y JSON creado")
    public void crear_DeberiaRetornarObjetoCreado() throws Exception {

        geolocalizacionModel guardado = new geolocalizacionModel();
        guardado.setId(2L);
        guardado.setUsuarioId(20L);
        guardado.setLatitud(-33.45);
        guardado.setLongitud(-70.66);

        when(geolocalizacionService.registrarReactivo(any(geolocalizacionModel.class)))
                .thenReturn(Mono.just(guardado));

        String json = """
                {
                    "usuarioId": 20,
                    "latitud": -33.45,
                    "longitud": -70.66
                }
                """;

        mockMvc.perform(post("/api/geolocalizacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.usuarioId").value(20))
                .andExpect(jsonPath("$.latitud").value(-33.45))
                .andExpect(jsonPath("$.longitud").value(-70.66));
    }

    @Test
    @DisplayName("GET /api/geolocalizacion/usuario/{usuarioId} -> Retorna 200 y JSON")
    public void buscarUltimaUbicacion_DeberiaRetornarUbicacion() throws Exception {

        geolocalizacionModel geo = new geolocalizacionModel();
        geo.setId(3L);
        geo.setUsuarioId(30L);
        geo.setLatitud(-33.40);
        geo.setLongitud(-70.60);

        when(geolocalizacionService.obtenerUltimaUbicacion(30L)).thenReturn(geo);

        mockMvc.perform(get("/api/geolocalizacion/usuario/30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.usuarioId").value(30))
                .andExpect(jsonPath("$.latitud").value(-33.40))
                .andExpect(jsonPath("$.longitud").value(-70.60));
    }

    @Test
    @DisplayName("GET /api/geolocalizacion/usuario/{usuarioId} -> Retorna 404 si no existe")
    public void buscarUltimaUbicacion_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(geolocalizacionService.obtenerUltimaUbicacion(88L)).thenReturn(null);

        mockMvc.perform(get("/api/geolocalizacion/usuario/88")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/geolocalizacion/{id} -> Retorna 200 y JSON actualizado")
    public void actualizar_CuandoExiste_DeberiaRetornarActualizado() throws Exception {

        geolocalizacionModel actualizado = new geolocalizacionModel();
        actualizado.setId(1L);
        actualizado.setUsuarioId(40L);
        actualizado.setLatitud(-33.10);
        actualizado.setLongitud(-70.10);

        when(geolocalizacionService.actualizar(any(Long.class), any(geolocalizacionModel.class)))
                .thenReturn(actualizado);

        String json = """
                {
                    "usuarioId": 40,
                    "latitud": -33.10,
                    "longitud": -70.10
                }
                """;

        mockMvc.perform(put("/api/geolocalizacion/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(40))
                .andExpect(jsonPath("$.latitud").value(-33.10))
                .andExpect(jsonPath("$.longitud").value(-70.10));
    }

    @Test
    @DisplayName("PUT /api/geolocalizacion/{id} -> Retorna 404 si no existe")
    public void actualizar_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(geolocalizacionService.actualizar(any(Long.class), any(geolocalizacionModel.class)))
                .thenReturn(null);

        String json = """
                {
                    "usuarioId": 40,
                    "latitud": -33.10,
                    "longitud": -70.10
                }
                """;

        mockMvc.perform(put("/api/geolocalizacion/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/geolocalizacion/{id} -> Retorna 204")
    public void eliminar_CuandoExiste_DeberiaRetornar204() throws Exception {

        when(geolocalizacionService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/geolocalizacion/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/geolocalizacion/{id} -> Retorna 404 si no existe")
    public void eliminar_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(geolocalizacionService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/geolocalizacion/99"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}