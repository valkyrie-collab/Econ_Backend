package com.valkyrie.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import feign.Request.HttpMethod;
import io.micrometer.common.lang.NonNull;
import jakarta.ws.rs.core.HttpHeaders;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfig() {

        return new WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(@NonNull CorsRegistry crsReg) {
                crsReg.addMapping("/**").allowedOrigins("http://localhost:5173")
                    .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
                    .allowedMethods(HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.DELETE.name());
            }

        };
        
    }
}
