package com.cymap.ms_personalizacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name = "webClientUsuarios")
    public WebClient webClientUsuarios() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api/usuarios")
                .build();
    }
}