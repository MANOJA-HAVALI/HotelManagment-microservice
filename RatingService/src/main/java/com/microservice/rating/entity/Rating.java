package com.microservice.rating.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ratings")
@Schema(description = "Rating entity")
public class Rating {
    
    @Id
    @Schema(description = "Unique rating identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String ratingId;
    
    @Schema(description = "User ID who gave the rating", example = "456e7890-e12b-34d5-a678-426614174000")
    private String userId;
    
    @Schema(description = "Hotel ID being rated", example = "789e0123-e45b-67d8-a901-426614174000")
    private String hotelId;
    
    @Schema(description = "Rating value (1-5)", example = "4", minimum = "1", maximum = "5")
    private int rating;
    
    @Schema(description = "User feedback/comments", example = "Excellent service and comfortable rooms!")
    private String feedback;

}