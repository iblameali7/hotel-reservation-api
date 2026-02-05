package com.hotel.controller;

import com.hotel.entity.Guest;
import com.hotel.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestRepository guestRepository;

    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        if (guestRepository.existsByEmail(guest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Guest savedGuest = guestRepository.save(guest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuest);
    }


    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        List<Guest> guests = guestRepository.findAll();
        return ResponseEntity.ok(guests);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        return guestRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable String email) {
        return guestRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        return guestRepository.findById(id)
                .map(guest -> {
                    guest.setName(guestDetails.getName());
                    guest.setEmail(guestDetails.getEmail());
                    Guest updatedGuest = guestRepository.save(guest);
                    return ResponseEntity.ok(updatedGuest);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}