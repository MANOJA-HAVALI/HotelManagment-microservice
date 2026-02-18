package com.auth.service.extrenal;

import com.auth.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServiceClient {

    @GetMapping("/hotels/Get/{id}")
    Hotel getHotel(@PathVariable("id") String id);
}
