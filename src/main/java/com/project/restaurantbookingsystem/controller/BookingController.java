package com.project.restaurantbookingsystem.controller;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation createdReservation = bookingService.createNewReservation(reservationDto);
        return ResponseEntity.ok(createdReservation);
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@RequestBody ReservationDto reservationDto) {
        Reservation cancelledReservation = bookingService.cancelReservation(reservationDto);
        return ResponseEntity.ok(cancelledReservation);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReservation(@RequestBody UpdateReservationDto updateReservationDto) {
        Reservation cancelledReservation = bookingService.updateReservation(updateReservationDto);
        return ResponseEntity.ok(cancelledReservation);
    }

    @GetMapping(value = "/table/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTableAvailability(@RequestParam("id") Long restaurantId,
                                                  @RequestParam("capacity") Long capacity,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, List<DiningTable>> tableAvailabilityMap =
                bookingService.findTablesByRestaurantIdAndSeatingCapacityAndDateRange(restaurantId, capacity, startDate, endDate);
        Map<LocalDate, List<DiningTableDto>> tableAvailabilityMapDto =
                bookingService.getTableAvailabilityDtoMap(tableAvailabilityMap);
        return ResponseEntity.ok(tableAvailabilityMapDto);
    }
}
