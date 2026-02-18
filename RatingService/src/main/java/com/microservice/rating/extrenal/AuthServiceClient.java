package com.microservice.rating.extrenal;


import com.microservice.rating.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Auth-Service")
public interface AuthServiceClient {

    @PostMapping("/auth/validate")
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String token);
}
