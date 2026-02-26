package com.microservice.rating.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ratingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rating Service API")
                        .description("Rating Service API Documentation")
                        .version("1.0"))
                .servers(List.of(new Server().url("http://localhost:8082")
                ));
    }
}
