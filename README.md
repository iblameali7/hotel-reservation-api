Hotel Reservation System

A lightweight RESTful API for hotel reservation management.

Overview

This is a backend project that provides a REST API for managing hotel rooms and guests.
It is built using Spring Boot and PostgreSQL, following a simple layered architecture (Controller – Repository – Entity).

All requests and responses are handled in JSON format.

Current Status: Work in Progress (WIP)

Technologies

Language: Java 17

Framework: Spring Boot

Build Tool: Maven

Database: PostgreSQL

API Style: REST

Features

Rooms: Create, retrieve, update, delete, and filter rooms by type and price.

Guests: Create, retrieve, update, and delete guest profiles.

Project Structure

The source code is located in src/main/java/com/hotel.

Key packages include:

entity – JPA entities for Room and Guest

repository – Data access layer using Spring Data JPA

controller – REST controllers for API endpoints

The main entry point is HotelReservationApplication.java.

Documentation

API endpoints and example requests can be found directly in the source code and controllers.