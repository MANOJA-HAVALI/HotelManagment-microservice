package com.hotel.service.HotelService.service;

import com.hotel.service.HotelService.entites.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create(Hotel hotel);
    List<Hotel> getAll();
    Hotel get(String id);
    Hotel update(Hotel hotel);
    void delete(String id);
}
