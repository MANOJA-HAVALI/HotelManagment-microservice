package com.hotel.service.HotelService.repository;

import com.hotel.service.HotelService.entites.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel , String> {
    List<Hotel> findByDeletedFalse();

    Optional<Hotel> findByIdAndDeletedFalse(String id);
}
