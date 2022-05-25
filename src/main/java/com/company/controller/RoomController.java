package com.company.controller;

import com.company.exceptions.PageSizeException;
import com.company.payload.RoomDto;
import com.company.repository.AppConstants;
import com.company.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/getRoomsByHotelId/{hotel_id}")
    public HttpEntity<?> getRoomsByHotelId(
            @PathVariable Long hotel_id,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(value = "all", defaultValue = "true") boolean all
    ) throws PageSizeException {
        return ResponseEntity.ok(roomService.getAllRoomsByHotelId(hotel_id, page, size, all));
    }


    @PostMapping("/saveOrDelete")
    public HttpEntity<?> saveOrEdit(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.saveOrEdit(dto));
    }

    @GetMapping("/all")
    public HttpEntity<?> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/getRoomById/{id}")
    public HttpEntity<?> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.remove(id));
    }

}
