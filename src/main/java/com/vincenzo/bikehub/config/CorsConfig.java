package com.vincenzo.bikehub.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Applica a tutte le API del tuo backend
                        .allowedOrigins("http://localhost:4200") // L'origine del tuo frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodi HTTP permessi
                        .allowedHeaders("*") // Permette tutti gli header
                        .allowCredentials(true) // Importante per sessioni, cookie, autorizzazione con credenziali (es. JWT)
                        .maxAge(3600); // Durata (in secondi) della validità della risposta pre-flight CORS
            }
        };
    }
}