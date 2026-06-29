package com.cymap.ms_comentarios.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cymap.ms_comentarios.controller.comentariosController;
import com.cymap.ms_comentarios.model.comentariosModel;
import com.cymap.ms_comentarios.service.comentariosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class ComentariosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private comentariosService comentariosService;

    @InjectMocks
    private comentariosController comentariosController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(comentariosController).build();
    }

    @Test
    @DisplayName("GET /api/comentarios/{id} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarComentario() throws Exception {

        comentariosModel comentarioFalso = new comentariosModel();
        comentarioFalso.setId(1L);
        comentarioFalso.setRutaId(10L);
        comentarioFalso.setUsuarioId(5L);
        comentarioFalso.setContenido("Muy buena ruta");

        when(comentariosService.obtenerPorId(1L)).thenReturn(comentarioFalso);

        mockMvc.perform(get("/api/comentarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutaId").value(10))
                .andExpect(jsonPath("$.usuarioId").value(5))
                .andExpect(jsonPath("$.contenido").value("Muy buena ruta"));
    }

    @Test
    @DisplayName("GET /api/comentarios -> Retorna 200 y lista de comentarios")
    public void listar_DeberiaRetornarListaComentarios() throws Exception {

        comentariosModel comentario = new comentariosModel();
        comentario.setId(1L);
        comentario.setRutaId(10L);
        comentario.setUsuarioId(5L);
        comentario.setContenido("Muy buena ruta");

        when(comentariosService.listar()).thenReturn(List.of(comentario));

        mockMvc.perform(get("/api/comentarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rutaId").value(10))
                .andExpect(jsonPath("$[0].usuarioId").value(5))
                .andExpect(jsonPath("$[0].contenido").value("Muy buena ruta"));
    }

    @Test
    @DisplayName("POST /api/comentarios -> Retorna 200 y objeto creado")
    public void crearComentario_DeberiaRetornarObjetoCreado() throws Exception {

        comentariosModel comentarioGuardado = new comentariosModel();
        comentarioGuardado.setId(2L);
        comentarioGuardado.setRutaId(12L);
        comentarioGuardado.setUsuarioId(7L);
        comentarioGuardado.setContenido("Comentario creado");

        when(comentariosService.registrar(any(comentariosModel.class))).thenReturn(comentarioGuardado);

        String jsonRequestBody = """
                {
                    "rutaId": 12,
                    "usuarioId": 7,
                    "contenido": "Comentario creado"
                }
                """;

        mockMvc.perform(post("/api/comentarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.rutaId").value(12))
                .andExpect(jsonPath("$.usuarioId").value(7))
                .andExpect(jsonPath("$.contenido").value("Comentario creado"));
    }

    @Test
    @DisplayName("GET /api/comentarios/ruta/{rutaId} -> Retorna comentarios por ruta")
    public void buscarPorRutaId_DeberiaRetornarComentarios() throws Exception {

        comentariosModel comentario = new comentariosModel();
        comentario.setId(3L);
        comentario.setRutaId(20L);
        comentario.setUsuarioId(9L);
        comentario.setContenido("Comentario de ruta");

        when(comentariosService.obtenerPorRutaId(20L)).thenReturn(List.of(comentario));

        mockMvc.perform(get("/api/comentarios/ruta/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].rutaId").value(20))
                .andExpect(jsonPath("$[0].usuarioId").value(9))
                .andExpect(jsonPath("$[0].contenido").value("Comentario de ruta"));
    }

    @Test
    @DisplayName("PUT /api/comentarios/{id} -> Retorna 200 y objeto actualizado")
    public void actualizarComentario_CuandoExiste_DeberiaRetornarComentarioActualizado() throws Exception {

        comentariosModel comentarioActualizado = new comentariosModel();
        comentarioActualizado.setId(1L);
        comentarioActualizado.setRutaId(30L);
        comentarioActualizado.setUsuarioId(11L);
        comentarioActualizado.setContenido("Comentario actualizado");

        when(comentariosService.actualizar(any(Long.class), any(comentariosModel.class)))
                .thenReturn(comentarioActualizado);

        String jsonRequestBody = """
                {
                    "rutaId": 30,
                    "usuarioId": 11,
                    "contenido": "Comentario actualizado"
                }
                """;

        mockMvc.perform(put("/api/comentarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rutaId").value(30))
                .andExpect(jsonPath("$.usuarioId").value(11))
                .andExpect(jsonPath("$.contenido").value("Comentario actualizado"));
    }

    @Test
    @DisplayName("DELETE /api/comentarios/{id} -> Retorna 204 si elimina correctamente")
    public void eliminarComentario_CuandoExiste_DeberiaRetornar204() throws Exception {

        when(comentariosService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/comentarios/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}