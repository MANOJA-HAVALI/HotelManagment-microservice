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
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Hotel Management Team")
                                .email("support@hotelmanagement.com")
                                .url("https://hotelmanagement.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8084")
                                .description("Development Server"),
                        new Server()
                                .url("https://rating.hotelmanagement.com")
                                .description("Production Server")
                ));
    }
}
