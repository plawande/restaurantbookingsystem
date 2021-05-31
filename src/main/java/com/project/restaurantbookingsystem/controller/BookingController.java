package com.project.restaurantbookingsystem.controller;

import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = bookingService.createNewReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@RequestBody Reservation reservation) {
        Reservation cancelledReservation = bookingService.cancelReservation(reservation);
        return ResponseEntity.ok(cancelledReservation);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReservation(@RequestBody UpdateReservationDto updateReservationDto) {
        Reservation cancelledReservation = bookingService.updateReservation(updateReservationDto);
        return ResponseEntity.ok(cancelledReservation);
    }
}
