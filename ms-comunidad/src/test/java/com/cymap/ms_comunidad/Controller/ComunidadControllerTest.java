package com.cymap.ms_comunidad.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cymap.ms_comunidad.controller.comunidadController;
import com.cymap.ms_comunidad.model.comunidadModel;
import com.cymap.ms_comunidad.service.comunidadService;

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

import java.util.List;

public class ComunidadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private comunidadService comunidadService;

    @InjectMocks
    private comunidadController comunidadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(comunidadController).build();
    }

    @Test
    @DisplayName("GET /api/comunidad/{id} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarComunidad() throws Exception {

        comunidadModel comunidadFalsa = new comunidadModel();
        comunidadFalsa.setId(1L);
        comunidadFalsa.setNombreGrupo("Ciclistas Santiago");
        comunidadFalsa.setDescripcion("Grupo para ciclistas urbanos");
        comunidadFalsa.setUsuarioId(10L);

        when(comunidadService.obtenerPorId(1L)).thenReturn(comunidadFalsa);

        mockMvc.perform(get("/api/comunidad/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreGrupo").value("Ciclistas Santiago"))
                .andExpect(jsonPath("$.descripcion").value("Grupo para ciclistas urbanos"))
                .andExpect(jsonPath("$.usuarioId").value(10));
    }

    @Test
    @DisplayName("GET /api/comunidad/{id} -> Retorna 404 si el ID no existe")
    public void buscarPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(comunidadService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/comunidad/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/comunidad -> Retorna 200 y lista de comunidades")
    public void listar_DeberiaRetornarListaComunidades() throws Exception {

        comunidadModel comunidad = new comunidadModel();
        comunidad.setId(1L);
        comunidad.setNombreGrupo("Ciclistas Santiago");
        comunidad.setDescripcion("Grupo para ciclistas urbanos");
        comunidad.setUsuarioId(10L);

        when(comunidadService.listar()).thenReturn(List.of(comunidad));

        mockMvc.perform(get("/api/comunidad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreGrupo").value("Ciclistas Santiago"))
                .andExpect(jsonPath("$[0].descripcion").value("Grupo para ciclistas urbanos"))
                .andExpect(jsonPath("$[0].usuarioId").value(10));
    }

    @Test
    @DisplayName("POST /api/comunidad -> Retorna 200 y objeto creado")
    public void crearComunidad_DeberiaRetornarObjetoCreado() throws Exception {

        comunidadModel comunidadGuardada = new comunidadModel();
        comunidadGuardada.setId(2L);
        comunidadGuardada.setNombreGrupo("MTB Chile");
        comunidadGuardada.setDescripcion("Grupo de rutas de montaña");
        comunidadGuardada.setUsuarioId(5L);

        when(comunidadService.registrarReactivo(any(comunidadModel.class)))
                .thenReturn(Mono.just(comunidadGuardada));

        String jsonRequestBody = """
                {
                    "nombreGrupo": "MTB Chile",
                    "descripcion": "Grupo de rutas de montaña",
                    "usuarioId": 5
                }
                """;

        mockMvc.perform(post("/api/comunidad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombreGrupo").value("MTB Chile"))
                .andExpect(jsonPath("$.descripcion").value("Grupo de rutas de montaña"))
                .andExpect(jsonPath("$.usuarioId").value(5));
    }

    @Test
    @DisplayName("GET /api/comunidad/usuario/{usuarioId} -> Retorna comunidades por usuario")
    public void buscarPorUsuarioId_DeberiaRetornarComunidades() throws Exception {

        comunidadModel comunidad = new comunidadModel();
        comunidad.setId(3L);
        comunidad.setNombreGrupo("Ruta Costera");
        comunidad.setDescripcion("Comunidad de rutas costeras");
        comunidad.setUsuarioId(20L);

        when(comunidadService.obtenerPorUsuarioId(20L))
                .thenReturn(List.of(comunidad));

        mockMvc.perform(get("/api/comunidad/usuario/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].nombreGrupo").value("Ruta Costera"))
                .andExpect(jsonPath("$[0].descripcion").value("Comunidad de rutas costeras"))
                .andExpect(jsonPath("$[0].usuarioId").value(20));
    }

    @Test
    @DisplayName("PUT /api/comunidad/{id} -> Retorna 200 y objeto actualizado")
    public void actualizarComunidad_CuandoExiste_DeberiaRetornarComunidadActualizada() throws Exception {

        comunidadModel comunidadActualizada = new comunidadModel();
        comunidadActualizada.setId(1L);
        comunidadActualizada.setNombreGrupo("Grupo Actualizado");
        comunidadActualizada.setDescripcion("Descripción actualizada");
        comunidadActualizada.setUsuarioId(8L);

        when(comunidadService.actualizar(any(Long.class), any(comunidadModel.class)))
                .thenReturn(comunidadActualizada);

        String jsonRequestBody = """
                {
                    "nombreGrupo": "Grupo Actualizado",
                    "descripcion": "Descripción actualizada",
                    "usuarioId": 8
                }
                """;

        mockMvc.perform(put("/api/comunidad/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreGrupo").value("Grupo Actualizado"))
                .andExpect(jsonPath("$.descripcion").value("Descripción actualizada"))
                .andExpect(jsonPath("$.usuarioId").value(8));
    }

    @Test
    @DisplayName("DELETE /api/comunidad/{id} -> Retorna 204 si elimina correctamente")
    public void eliminarComunidad_CuandoExiste_DeberiaRetornar204() throws Exception {

        when(comunidadService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/comunidad/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}