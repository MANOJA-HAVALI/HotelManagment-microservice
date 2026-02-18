package com.hotel.service.HotelService.service.impl;

import com.hotel.service.HotelService.entites.Hotel;
import com.hotel.service.HotelService.exception.ResourceNotFoundException;
import com.hotel.service.HotelService.repository.HotelRepository;
import com.hotel.service.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImp implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hotel.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotel.getId()));
        existingHotel.setName(hotel.getName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setAbout(hotel.getAbout());
        return hotelRepository.save(existingHotel);
    }

    @Override
    public void delete(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        hotelRepository.delete(hotel);
    }
}
