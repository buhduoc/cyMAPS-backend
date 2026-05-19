package com.cymap.ms_comentarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientUsuarios(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080/api/usuarios")
                .build();
    }

    @Bean
    public WebClient webClientRutas(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081/rutas")
                .build();
    }
}