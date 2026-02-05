package com.hotel.controller;

import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;


    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (roomRepository.existsById(room.getRoomNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }


    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return ResponseEntity.ok(rooms);
    }


    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> getRoomByNumber(@PathVariable Integer roomNumber) {
        return roomRepository.findById(roomNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String roomType) {
        List<Room> rooms = roomRepository.findByRoomType(roomType);
        return ResponseEntity.ok(rooms);
    }


    @GetMapping("/cheaper-than/{maxPrice}")
    public ResponseEntity<List<Room>> getRoomsCheaperThan(@PathVariable Double maxPrice) {
        List<Room> rooms = roomRepository.findByPriceLessThanEqual(maxPrice);
        return ResponseEntity.ok(rooms);
    }


    @GetMapping("/sorted")
    public ResponseEntity<List<Room>> getRoomsSortedByPrice() {
        List<Room> rooms = roomRepository.findAllSortedByPrice();
        return ResponseEntity.ok(rooms);
    }


    @PutMapping("/{roomNumber}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer roomNumber, @RequestBody Room roomDetails) {
        return roomRepository.findById(roomNumber)
                .map(room -> {
                    room.setPrice(roomDetails.getPrice());
                    room.setRoomType(roomDetails.getRoomType());
                    Room updatedRoom = roomRepository.save(room);
                    return ResponseEntity.ok(updatedRoom);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer roomNumber) {
        if (roomRepository.existsById(roomNumber)) {
            roomRepository.deleteById(roomNumber);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
