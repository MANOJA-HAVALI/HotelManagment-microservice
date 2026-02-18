package com.hotel.service.HotelService.entites;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hotels")
@Schema(description = "Hotel entity")
public class Hotel {

    @Id
    @Schema(description = "Unique hotel identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "Hotel name", example = "Grand Plaza Hotel")
    private String name;
    
    @Schema(description = "Hotel location", example = "New York, USA")
    private String location;
    
    @Schema(description = "Hotel description", example = "Luxury hotel with premium amenities and excellent service")
    private String about;
}
