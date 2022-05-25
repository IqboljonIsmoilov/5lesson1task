package com.company.service;

import com.company.entity.Hotel;
import com.company.payload.ApiResponse;
import com.company.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public ApiResponse saveOrDelete(Hotel hotel) {
        if (hotel.getId() != null) {
            Hotel byId = hotelRepository.getById(hotel.getId());
            byId.setName(hotel.getName());
            hotelRepository.save(byId);
            return new ApiResponse(true, "Edited");
        } else {
            hotelRepository.save(hotel);
            return new ApiResponse(true, "Saved");
        }
    }

    public ApiResponse getHotelByID(Long id) {
        Optional<Hotel> optional = hotelRepository.findById(id);
        return optional.map(hotel -> new ApiResponse(true, "ID: " + id, hotel))
                .orElseGet(() -> new ApiResponse(false, "Not found Hotel"));
    }

    public ApiResponse getAll() {
        return new ApiResponse(true, "All hotels", hotelRepository.findAll());
    }

    public ApiResponse remove(Long id) {
        hotelRepository.deleteById(id);
        return new ApiResponse(true, "Deleted");
    }
}
