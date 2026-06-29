package com.cymap.ms_disponibilidad.Controller;

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

import com.cymap.ms_disponibilidad.controller.disponibilidadController;
import com.cymap.ms_disponibilidad.model.disponibilidadModel;
import com.cymap.ms_disponibilidad.service.disponibilidadService;

public class DisponibilidadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private disponibilidadService disponibilidadService;

    @InjectMocks
    private disponibilidadController disponibilidadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disponibilidadController).build();
    }

    @Test
    @DisplayName("GET disponibilidad por ID")
    void obtenerPorId() throws Exception {

        disponibilidadModel disp = new disponibilidadModel();
        disp.setId(1L);
        disp.setRutaId(5L);
        disp.setEstado("Disponible");
        disp.setMotivo("Normal");
        disp.setUltimaActualizacion("2026-06-29");

        when(disponibilidadService.obtenerPorId(1L)).thenReturn(disp);

        mockMvc.perform(get("/api/disponibilidad/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutaId").value(5));
    }

    @Test
    @DisplayName("GET listar disponibilidades")
    void listar() throws Exception {

        disponibilidadModel disp = new disponibilidadModel();
        disp.setId(1L);
        disp.setRutaId(5L);
        disp.setEstado("Disponible");
        disp.setMotivo("Normal");
        disp.setUltimaActualizacion("2026-06-29");

        when(disponibilidadService.listar()).thenReturn(List.of(disp));

        mockMvc.perform(get("/api/disponibilidad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutaId").value(5));
    }

    @Test
    @DisplayName("GET disponibilidad por Ruta")
    void obtenerPorRuta() throws Exception {

        disponibilidadModel disp = new disponibilidadModel();
        disp.setId(2L);
        disp.setRutaId(10L);
        disp.setEstado("Disponible");
        disp.setMotivo("Libre");
        disp.setUltimaActualizacion("2026-06-29");

        when(disponibilidadService.obtenerPorRutaId(10L)).thenReturn(disp);

        mockMvc.perform(get("/api/disponibilidad/ruta/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutaId").value(10));
    }

    @Test
    @DisplayName("POST crear disponibilidad")
    void crear() throws Exception {

        disponibilidadModel guardado = new disponibilidadModel();
        guardado.setId(3L);
        guardado.setRutaId(20L);
        guardado.setEstado("Disponible");
        guardado.setMotivo("OK");
        guardado.setUltimaActualizacion("2026-06-29");

        when(disponibilidadService.registrarReactivo(any(disponibilidadModel.class)))
                .thenReturn(Mono.just(guardado));

        String body = """
        {
            "rutaId":20,
            "estado":"Disponible",
            "motivo":"OK",
            "ultimaActualizacion":"2026-06-29"
        }
        """;

        mockMvc.perform(post("/api/disponibilidad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT actualizar disponibilidad")
    void actualizar() throws Exception {

        disponibilidadModel actualizado = new disponibilidadModel();
        actualizado.setId(1L);
        actualizado.setRutaId(30L);
        actualizado.setEstado("No Disponible");
        actualizado.setMotivo("Mantención");
        actualizado.setUltimaActualizacion("2026-06-29");

        when(disponibilidadService.actualizar(any(Long.class), any(disponibilidadModel.class)))
                .thenReturn(actualizado);

        String body = """
        {
            "rutaId":30,
            "estado":"No Disponible",
            "motivo":"Mantención",
            "ultimaActualizacion":"2026-06-29"
        }
        """;

        mockMvc.perform(put("/api/disponibilidad/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutaId").value(30));
    }

    @Test
    @DisplayName("DELETE disponibilidad")
    void eliminar() throws Exception {

        when(disponibilidadService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/disponibilidad/1"))
                .andExpect(status().isNoContent());
    }
}