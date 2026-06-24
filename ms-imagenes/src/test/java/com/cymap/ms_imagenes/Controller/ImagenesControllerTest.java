package com.cymap.ms_imagenes.Controller;

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


import com.cymap.ms_imagenes.controller.imagenesController;
import com.cymap.ms_imagenes.Assamblers.ImagenesModelAssamblers;
import com.cymap.ms_imagenes.model.imagenesModel;
import com.cymap.ms_imagenes.service.imagenesService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(imagenesController.class)
@Import(imagenesController.class)
public class ImagenesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private imagenesService imagenesService;

    @Test
    @DisplayName("GET http://localhost:8084/api/imagenes/ruta/{id} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarArchivoRuta() throws Exception {

        // 1. ARRANGE: Configuración de los datos falsos con la nueva estructura
        imagenesModel tipoFalso = new imagenesModel();
        tipoFalso.setId(1L);
        tipoFalso.setRutaId(500L);
        tipoFalso.setUrlStorage("https://storage.googleapis.com/cymaps/rutas/archivo01.gpx");
        tipoFalso.setPesoKb(2048);

        // Mock del servicio reactivo regresando el objeto simulado wrapped en un Mono
        when(imagenesService.obtenerPorId(1L)).thenReturn(tipoFalso);

        // 2. ACT & 3. ASSERT: Ejecución y verificación de las propiedades correspondientes
        mockMvc.perform(get("/api/imagenes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutaId").value(500))
                .andExpect(jsonPath("$.urlStorage").value("https://storage.googleapis.com/cymaps/rutas/archivo01.gpx"))
                .andExpect(jsonPath("$.pesoKb").value(2048));
    }

    @Test
    @DisplayName("\"GET http://localhost:8084/api/imagenes/ruta/{id} -> Retorna 200 y JSON si el ID existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(imagenesService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/imagenes/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}
