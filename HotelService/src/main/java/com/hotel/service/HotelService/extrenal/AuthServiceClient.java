package com.hotel.service.HotelService.extrenal;

import com.hotel.service.HotelService.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Auth-Service")
public interface AuthServiceClient {

    @PostMapping(value = "/auth/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String token);
}
