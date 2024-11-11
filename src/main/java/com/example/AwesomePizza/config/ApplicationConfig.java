package com.example.AwesomePizza.config;

import io.swagger.v3.oas.models.OpenAPI;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.swagger.v3.oas.models.info.Info;


@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Solace API")
                        .version("1.0")
                        .description("API for managing surveys and related questions"));
    }
}