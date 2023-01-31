package com.example.airbnbb7.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
<<<<<<< HEAD
    private static final String API_KEY = "Bearer Token";
=======
    private static final String API_KEY = "Bearer Token ";
>>>>>>> f80b5ac2e15977e6d61b619bfb03be351ac8318b

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecuritySchema())) // define the apiKey SecuritySchema
<<<<<<< HEAD
                .info(new Info().title("Task Tracker"))
=======
                .info(new Info().title("Airbnb"))
>>>>>>> f80b5ac2e15977e6d61b619bfb03be351ac8318b
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY))); // then apply it. If you don't apply it will not be added to the header in cURL
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization") // authorisation-token
                .description("Just put the token")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer");
    }
}
