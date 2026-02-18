package com.microservice.rating.controller;

import com.microservice.rating.entity.Rating;
import com.microservice.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ratings")
@Tag(name = "Rating Management", description = "Rating management APIs")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    @Operation(summary = "Create a new rating", description = "Creates a new rating with auto-generated ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rating created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
        String randomRatingId = UUID.randomUUID().toString();
        rating.setRatingId(randomRatingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    @GetMapping
    @Operation(summary = "Get all ratings", description = "Retrieves a list of all ratings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully")
    })
    public ResponseEntity<List<Rating>> getRatings() {
        return ResponseEntity.ok(ratingService.getRatings());
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get ratings by user ID", description = "Retrieves all ratings given by a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<Rating>> getRatingsByUserId(@Parameter(description = "User ID", required = true) @PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    @GetMapping("/hotels/{hotelId}")
    @Operation(summary = "Get ratings by hotel ID", description = "Retrieves all ratings for a specific hotel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@Parameter(description = "Hotel ID", required = true) @PathVariable String hotelId) {
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }
}


