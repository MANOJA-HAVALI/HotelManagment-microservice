package com.hotel.service.HotelService.controller;

import com.hotel.service.HotelService.entites.Hotel;
import com.hotel.service.HotelService.service.HotelService;
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
@RequestMapping("/hotels")
@Tag(name = "Hotel Management", description = "Hotel management APIs")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/Create")
    @Operation(summary = "Create a new hotel", description = "Creates a new hotel with auto-generated ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Hotel created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    @GetMapping("/GetAll")
    @Operation(summary = "Get all hotels", description = "Retrieves a list of all hotels")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotels retrieved successfully")
    })
    public ResponseEntity<List<Hotel>> getAll(){
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/Get/{id}")
    @Operation(summary = "Get hotel by ID", description = "Retrieves a specific hotel by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    public ResponseEntity<Hotel> getHotel(@Parameter(description = "Hotel ID", required = true) @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(id));
    }

    @PutMapping("/Update/{id}")
    @Operation(summary = "Update hotel", description = "Updates an existing hotel by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
        @ApiResponse(responseCode = "404", description = "Hotel not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Hotel> updateHotel(@Parameter(description = "Hotel ID", required = true) @PathVariable String id, @RequestBody Hotel hotel) {
        hotel.setId(id);
        return ResponseEntity.ok(hotelService.update(hotel));
    }

    @DeleteMapping("/Delete/{id}")
    @Operation(summary = "Delete hotel", description = "Deletes a hotel by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    public ResponseEntity<String> deleteHotel(@Parameter(description = "Hotel ID", required = true) @PathVariable String id) {
        hotelService.delete(id);
        return ResponseEntity.ok("Hotel deleted successfully");
    }
}
