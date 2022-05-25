package com.company.service;

import com.company.entity.Room;
import com.company.exceptions.PageSizeException;
import com.company.payload.ApiResponse;
import com.company.payload.RoomDto;
import com.company.repository.RoomRepository;
import com.company.utills.CommandUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public ApiResponse getAllRoomsByHotelId(Long hotelId, Integer page, Integer size, boolean all) throws PageSizeException {
        List<RoomDto> roomDtoList = null;
        List<Room> roomList = null;
        Page<Room> roomPage = roomRepository.findAll(CommandUtills.simplePageable(page - 1, size));

        for (Room room : roomPage.getContent()) {
            if (Objects.equals(room.getHotel().getId(), hotelId)) {
                roomList.add(room);
            }
        }

        roomDtoList = roomList.stream()
                .map(this::generateRoomDto).collect(Collectors.toList());
        return new ApiResponse(true, "All Rooms", roomDtoList, roomPage.getTotalElements());
    }

    public ApiResponse saveOrEdit(RoomDto roomDto) {
        if (roomDto.getId() != null) {
            Room byId = roomRepository.getById(roomDto.getId());
            byId.setFloor(roomDto.getFloor());
            byId.setHotel(roomDto.getHotel());
            byId.setNumber(roomDto.getNumber());
            byId.setSize(roomDto.getSize());
            roomRepository.save(byId);
            return new ApiResponse(true, "Edited");
        } else {
            roomRepository.save(generateRoom(roomDto));
            return new ApiResponse(true, "Saved");
        }
    }

    private Room generateRoom(RoomDto dto) {
        Room room = new Room();
        room.setSize(dto.getSize());
        room.setHotel(dto.getHotel());
        room.setNumber(dto.getNumber());
        room.setFloor(dto.getFloor());
        return room;
    }

    private RoomDto generateRoomDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getNumber(),
                room.getFloor(),
                room.getSize(),
                room.getHotel()
        );
    }

    public ApiResponse remove(Long room_id) {
        Optional<Room> optional = roomRepository.findById(room_id);
        if (optional.isPresent()) {
            roomRepository.deleteById(room_id);
            return new ApiResponse(true, "Deleted");
        } else {
            return new ApiResponse(false, "Not found room");
        }
    }

    public ApiResponse getRoomById(Long room_id) {
        Optional<Room> optional = roomRepository.findById(room_id);
        return optional.map(room -> new ApiResponse(true, "Id: " + room_id, room))
                .orElseGet(() -> new ApiResponse(false, "Not found room"));
    }

    public ApiResponse getAllRooms() {
        return new ApiResponse(true, "All rooms", roomRepository.findAll());
    }
}
