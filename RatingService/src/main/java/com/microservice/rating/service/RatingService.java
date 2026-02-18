package com.microservice.rating.service;

import com.microservice.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    public Rating create(Rating rating);
    List<Rating> getRatings();
    List<Rating> getRatingByUserId(String userId);
    List<Rating> getRatingByHotelId(String hotelId);
}
