package com.hotel.repository;

import com.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByRoomType(String roomType);
    List<Room> findByPriceLessThanEqual(Double maxPrice);

    @Query("SELECT r FROM Room r ORDER BY r.price ASC")
    List<Room> findAllSortedByPrice();
}