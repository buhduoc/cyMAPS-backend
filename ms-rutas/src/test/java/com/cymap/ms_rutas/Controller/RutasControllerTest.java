package com.cymap.ms_rutas.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cymap.ms_rutas.Assamblers.RutasModelAssamblers;
import com.cymap.ms_rutas.controller.rutasController;
import com.cymap.ms_rutas.model.rutasModel;
import com.cymap.ms_rutas.service.rutasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RutasControllerTest {

    private MockMvc mockMvc;

    @Mock
    private rutasService rutasService;

    @Mock
    private RutasModelAssamblers assembler;

    @InjectMocks
    private rutasController rutasController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rutasController).build();
    }

    @Test
    @DisplayName("GET /rutas/{id} -> Retorna 200 y JSON si el ID existe")
    public void obtenerPorId_CuandoExiste_DeberiaRetornarRuta() throws Exception {

        rutasModel rutaFalsa = new rutasModel();
        rutaFalsa.setId(1L);
        rutaFalsa.setNombre("Ruta Centro");
        rutaFalsa.setTipo("Urbana");
        rutaFalsa.setUsuarioId(10L);

        when(rutasService.obtenerPorId(1L)).thenReturn(rutaFalsa);

        mockMvc.perform(get("/rutas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Ruta Centro"))
                .andExpect(jsonPath("$.tipo").value("Urbana"))
                .andExpect(jsonPath("$.usuarioId").value(10));
    }

    @Test
    @DisplayName("GET /rutas/{id} -> Retorna 404 si el ID no existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(rutasService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/rutas/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /rutas -> Retorna 200 y el objeto creado")
    public void crearRuta_DeberiaRetornarObjetoCreado() throws Exception {

        rutasModel rutaGuardada = new rutasModel();
        rutaGuardada.setId(2L);
        rutaGuardada.setNombre("Ruta Playa");
        rutaGuardada.setTipo("Ciclovía");
        rutaGuardada.setUsuarioId(5L);

        when(rutasService.guardarRuta(any(rutasModel.class))).thenReturn(rutaGuardada);

        String jsonRequestBody = """
                {
                    "nombre": "Ruta Playa",
                    "tipo": "Ciclovía",
                    "usuarioId": 5
                }
                """;

        mockMvc.perform(post("/rutas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Ruta Playa"))
                .andExpect(jsonPath("$.tipo").value("Ciclovía"))
                .andExpect(jsonPath("$.usuarioId").value(5));
    }

    @Test
    @DisplayName("PUT /rutas/{id} -> Retorna 200 y objeto actualizado")
    public void actualizarRuta_CuandoExiste_DeberiaRetornarRutaActualizada() throws Exception {

        rutasModel rutaActualizada = new rutasModel();
        rutaActualizada.setId(1L);
        rutaActualizada.setNombre("Ruta Actualizada");
        rutaActualizada.setTipo("Montaña");
        rutaActualizada.setUsuarioId(8L);

        when(rutasService.actualizar(any(Long.class), any(rutasModel.class)))
                .thenReturn(rutaActualizada);

        String jsonRequestBody = """
                {
                    "nombre": "Ruta Actualizada",
                    "tipo": "Montaña",
                    "usuarioId": 8
                }
                """;

        mockMvc.perform(put("/rutas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Ruta Actualizada"))
                .andExpect(jsonPath("$.tipo").value("Montaña"))
                .andExpect(jsonPath("$.usuarioId").value(8));
    }

    @Test
    @DisplayName("DELETE /rutas/{id} -> Retorna 204 si elimina correctamente")
    public void eliminarRuta_CuandoExiste_DeberiaRetornar204() throws Exception {

        when(rutasService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/rutas/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}