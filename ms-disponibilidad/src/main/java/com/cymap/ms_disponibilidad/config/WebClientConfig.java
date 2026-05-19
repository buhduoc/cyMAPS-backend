package com.cymap.ms_disponibilidad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name = "webClientRutas")
    public WebClient webClientRutas() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/rutas")
                .build();
    }
}