package com.auth.service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Add internal service call header for inter-service communication
            requestTemplate.header("X-Internal-Service-Call", "true");
        };
    }
}
