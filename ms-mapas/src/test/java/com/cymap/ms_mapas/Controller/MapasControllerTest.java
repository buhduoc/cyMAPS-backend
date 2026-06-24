package com.cymap.ms_mapas.Controller;

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


import com.cymap.ms_mapas.controller.mapasController;
import com.cymap.ms_mapas.Assamblers.MapasModelAssamblers;
import com.cymap.ms_mapas.model.mapasModel;
import com.cymap.ms_mapas.service.mapasService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(mapasController.class)
@Import(MapasModelAssamblers.class)
public class MapasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private mapasService mapasService;

    @Test
    @DisplayName("GET http://localhost:8083/api/mapas/{id} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarCapa() throws Exception {

        // 1. ARRANGE: Creamos el objeto simulado con los atributos de tu entidad
        mapasModel tipoFalso = new mapasModel();
        tipoFalso.setId(1L);
        tipoFalso.setNombreCapa("Mapa Satelital");
        tipoFalso.setProveedor("Google Maps");
        tipoFalso.setActivo(true);

        // Mokeamos el servicio para que retorne el objeto (si usas reactivo, envuélvelo en Mono.just(tipoFalso))
        when(mapasService.obtenerPorId(1L)).thenReturn(tipoFalso);

        // 2. ACT & 3. ASSERT: Ejecución y verificación de las propiedades correspondientes
        mockMvc.perform(get("/api/mapas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCapa").value("Mapa Satelital"))
                .andExpect(jsonPath("$.proveedor").value("Google Maps"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    @DisplayName("GET http://localhost:8083/api/mapas/{id} -> Retorna 200 y JSON si el ID existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        when(mapasService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


}
