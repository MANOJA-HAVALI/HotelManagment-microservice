package com.auth.service.extrenal;

import com.auth.service.config.FeignClientConfig;
import com.auth.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE", configuration = FeignClientConfig.class)
public interface RatingServiceClient {

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable("userId") String userId);
}


