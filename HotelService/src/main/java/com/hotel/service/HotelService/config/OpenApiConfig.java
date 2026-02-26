package com.hotel.service.HotelService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Service API")
                        .description("Hotel Management Service API Documentation")
                        .version("1.0"))
                .servers(List.of(new Server().url("http://localhost:8081")
                ));
    }
}
