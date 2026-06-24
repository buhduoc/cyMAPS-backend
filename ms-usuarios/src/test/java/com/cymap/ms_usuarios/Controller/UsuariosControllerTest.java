package com.cymap.ms_usuarios.Controller;

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





import com.cymap.ms_usuarios.controller.usuariosController;
import com.cymap.ms_usuarios.Assamblers.UsuariosModelAssamblers;
import com.cymap.ms_usuarios.model.usuariosModel;
import com.cymap.ms_usuarios.service.usuarioService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(usuariosController.class)
@Import(UsuariosModelAssamblers.class)

public class UsuariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private usuarioService usuarioService;

    @Test

    @DisplayName("GET http://localhost:8080/api/usuarios/{id} -> Retorna 200 y JSON si el ID existe")
    public void buscarPorId_CuandoExiste_DeberiaRetornarTipoUsuario() throws Exception {

        usuariosModel tipoFalso = new usuariosModel();
        tipoFalso.setId(1L);

        tipoFalso.setNombre("Alex");
        tipoFalso.setEmail("alex@gmail.com");
        tipoFalso.setPassword("123456");

        when(usuarioService.obtenerPorId(1L)).thenReturn(tipoFalso);


        mockMvc.perform(get("/api/usuarios/1")

                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Imprime la respuesta en consola para debugear
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@gmail.com"));




    }

    @Test
    @DisplayName("GET /api/usuarios/{id} -> Retorna 404 si el ID no existe")
    public void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {

        // 1. ARRANGE (Como vimos que tu servicio no usa Optional sino que devuelve null o lanza excepción)
        // Simulamos que al buscar el ID 99L, el servicio regresa null
        when(usuarioService.obtenerPorId(99L)).thenReturn(null);

        // 2. ACT & 3. ASSERT
        mockMvc.perform(get("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        // Nota: Si tu controlador maneja una excepción global (como @RestControllerAdvice),
        // puedes descomentar la siguiente línea para validar el mensaje exacto que configuraste:
        // .andExpect(jsonPath("$.mensaje").value("No existe el usuario con ID: 99"));
    }

    @Test
    @DisplayName("POST /api/usuarios -> Retorna 200 y el objeto creado")
    public void registrarUsuario_DeberiaRetornarObjetoCreado() throws Exception {

        // 1. ARRANGE
        usuariosModel usuarioGuardado = new usuariosModel();
        usuarioGuardado.setId(2L);
        usuarioGuardado.setNombre("Alex");
        usuarioGuardado.setEmail("alex@gmail.com");
        usuarioGuardado.setPassword("654321");

        // Configuramos el Mock usando tu método real 'registrar'
        // org.mockito.ArgumentMatchers.any se usa para aceptar cualquier instancia de la clase
        when(usuarioService.registrar(any(usuariosModel.class))).thenReturn(usuarioGuardado);

        // Definimos el JSON de entrada (Asegúrate de que use la estructura de tus atributos)
        String jsonRequestBody = """
                {
                    "nombre": "Alex",
                    "email": "alex@gmail.com",
                    "password": "654321"
                }
                """;

        // 2. ACT & 3. ASSERT
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk()) // Cambiar a isCreated() si tu controlador usa HttpStatus.CREATED
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@gmail.com"));
    }


}
