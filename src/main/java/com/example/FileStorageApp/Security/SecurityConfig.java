package com.example.FileStorageApp.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http)throws Exception{
        http
        .cors().and()
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request.requestMatchers("/authentication/**")
        .permitAll()        
        .anyRequest().authenticated())
        .oauth2ResourceServer(auth -> auth.jwt((jwt)-> jwt.jwtAuthenticationConverter(new TokenConverter())));        
        return http.build();
    }
    
}