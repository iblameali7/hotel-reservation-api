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

    // CREATE - Создать новую комнату
    // POST http://localhost:8080/api/rooms
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (roomRepository.existsById(room.getRoomNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // READ - Получить все комнаты
    // GET http://localhost:8080/api/rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return ResponseEntity.ok(rooms);
    }

    // READ - Получить комнату по номеру
    // GET http://localhost:8080/api/rooms/101
    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> getRoomByNumber(@PathVariable Integer roomNumber) {
        return roomRepository.findById(roomNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ - Получить комнаты по типу
    // GET http://localhost:8080/api/rooms/type/Suite
    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String roomType) {
        List<Room> rooms = roomRepository.findByRoomType(roomType);
        return ResponseEntity.ok(rooms);
    }

    // READ - Получить комнаты дешевле определенной цены
    // GET http://localhost:8080/api/rooms/cheaper-than/600
    @GetMapping("/cheaper-than/{maxPrice}")
    public ResponseEntity<List<Room>> getRoomsCheaperThan(@PathVariable Double maxPrice) {
        List<Room> rooms = roomRepository.findByPriceLessThanEqual(maxPrice);
        return ResponseEntity.ok(rooms);
    }

    // READ - Получить комнаты отсортированные по цене
    // GET http://localhost:8080/api/rooms/sorted
    @GetMapping("/sorted")
    public ResponseEntity<List<Room>> getRoomsSortedByPrice() {
        List<Room> rooms = roomRepository.findAllSortedByPrice();
        return ResponseEntity.ok(rooms);
    }

    // UPDATE - Обновить данные комнаты
    // PUT http://localhost:8080/api/rooms/101
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

    // DELETE - Удалить комнату
    // DELETE http://localhost:8080/api/rooms/101
    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer roomNumber) {
        if (roomRepository.existsById(roomNumber)) {
            roomRepository.deleteById(roomNumber);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
