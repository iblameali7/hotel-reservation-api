package com.hotel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(nullable = false)
    private Double price;

    @Column(name = "room_type", nullable = false, length = 20)
    private String roomType;

    public Room() {
    }

    public Room(Integer roomNumber, Double price, String roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    public Double calculateTotal() {
        if ("Suite".equalsIgnoreCase(roomType)) {
            return price * 1.3;
        }
        return price;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", price=" + price +
                ", roomType='" + roomType + '\'' +
                ", finalPrice=" + calculateTotal() +
                '}';
    }
}