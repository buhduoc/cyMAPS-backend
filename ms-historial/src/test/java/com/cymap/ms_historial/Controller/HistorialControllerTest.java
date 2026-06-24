package com.cymap.ms_historial.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.cymap.ms_historial.controller.historialController;
import com.cymap.ms_historial.Assamblers.HistorialModelAssamblers;
import com.cymap.ms_historial.model.historialModel;
import com.cymap.ms_historial.service.historialService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(historialController.class)
@Import(historialModel.class)
public class HistorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private historialService historialService;

    @Test
    @DisplayName("http://localhost:8085/api/historial/usuario/{usuarioId} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarRutaRealizada() throws Exception {

        // 1. ARRANGE: Configuramos el objeto simulado con tus nuevos atributos
        historialModel tipoFalso = new historialModel(); // Usa el nombre exacto de tu clase modelo
        tipoFalso.setId(1L);
        tipoFalso.setUsuarioId(123L);
        tipoFalso.setRutaId(500L);
        tipoFalso.setFechaRealizada("2026-06-23");
        tipoFalso.setTiempoMinutos(45);

        // Mokeamos el servicio correspondiente (envuelto en Mono.just si es reactivo)
        // Asegúrate de cambiar 'rutasRealizadasService' por el nombre real de tu variable de servicio
        when(historialService.obtenerPorId(1L)).thenReturn(tipoFalso);

        // 2. ACT & 3. ASSERT: Ejecución y verificación de las propiedades en el JSON
        // Recuerda validar que el endpoint coincida con el de tu controlador (ej. /api/rutas-realizadas/1)
        mockMvc.perform(get("/api/historial/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(123))
                .andExpect(jsonPath("$.rutaId").value(500))
                .andExpect(jsonPath("$.fechaRealizada").value("2026-06-23"))
                .andExpect(jsonPath("$.tiempoMinutos").value(45));
    }

    @Test
    @DisplayName("http://localhost:8085/api/historial/usuario/{usuarioId} -> Retorna 200 y JSON si el ID existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(historialService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/historial/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

}
