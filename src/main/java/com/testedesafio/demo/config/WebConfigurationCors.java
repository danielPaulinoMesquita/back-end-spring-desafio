package com.testedesafio.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfigurationCors implements WebMvcConfigurer {

    private static final String FRONT_ANGULAR = "http://localhost:4200";
    private static final String FRONT_REACT = "http://localhost:3000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
    }
}
