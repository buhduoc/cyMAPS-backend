package com.cymap.ms_personalizacion.Controller;

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
import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.service.personalizacionService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cymap.ms_personalizacion.controller.personalizacionController;
import com.cymap.ms_personalizacion.model.personalizacionModel;
import com.cymap.ms_personalizacion.service.personalizacionService;
import com.cymap.ms_personalizacion.Assamblers.PersonalizacionModelAssamblers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(personalizacionController.class)
@Import(PersonalizacionModelAssamblers.class)
public class PersonalizacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private personalizacionService personalizacionService;

    @Test
    @DisplayName("GET http://localhost:8082/api/personalizacion/{id} -> Retorna 200 si el usuario tiene preferencias")
    public void buscarPorId_CuandoExiste_DeberiaRetornarTipoUsuario() throws Exception {

        // 1. ARRANGE
        personalizacionModel tipoFalso = new personalizacionModel();
        tipoFalso.setId(1L);
        tipoFalso.setUsuarioId(123L);
        tipoFalso.setColorRutaHex("#FF5733");
        tipoFalso.setModoOscuro(true);

        // Mokeamos el servicio para que retorne un flujo reactivo Mono
        when(personalizacionService.obtenerPorUsuarioId(123L)).thenReturn(tipoFalso);

        mockMvc.perform(get("/api/personalizacion/usuario/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colorRutaHex").value("#FF5733"))
                .andExpect(jsonPath("$.modoOscuro").value(true));


    }
    @Test
    @DisplayName("GET http://localhost:8080/api/personalizacion/usuario/{id} -> Retorna 404 si el ID no existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        // 1. ARRANGE
        // Simulamos que al buscar el usuario 99L, el servicio no encuentra nada (Mono vacío)
        when(personalizacionService.obtenerPorUsuarioId(99L)).thenReturn(null);

        // 2. ACT & 3. ASSERT
        mockMvc.perform(get("/api/personalizacion/usuario/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isNotFound());
    }


}
