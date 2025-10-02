package com._bits.reservas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desactiva CSRF para facilitar pruebas con Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/reservas/registro").permitAll() // Público solo este endpoint
                .anyRequest().authenticated() // El resto sigue protegido
            )
            .httpBasic(); // Mantiene Basic Auth para lo que sigue protegido
        return http.build();
    }
}

