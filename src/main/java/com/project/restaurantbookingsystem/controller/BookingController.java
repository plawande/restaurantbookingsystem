package com.project.restaurantbookingsystem.controller;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.RestaurantDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.BookingService;
import com.project.restaurantbookingsystem.service.EntityDtoMapper;
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

    @Autowired
    private EntityDtoMapper entityDtoMapper;

    @GetMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRestaurants() {
        List<Restaurant> restaurants = bookingService.findAllRestaurants();
        List<RestaurantDto> restaurantsDtoList = entityDtoMapper.getRestaurantsDtoList(restaurants);
        return ResponseEntity.ok(restaurantsDtoList);
    }

    @GetMapping(value = "/restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = bookingService.findRestaurantById(id);
        RestaurantDto restaurantsDto = entityDtoMapper.createRestaurantDto(restaurant);
        return ResponseEntity.ok(restaurantsDto);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation createdReservation = bookingService.createNewReservation(reservationDto);
        ReservationDto reservationDtoCreated = entityDtoMapper.createReservationDto(createdReservation);
        return ResponseEntity.ok(reservationDtoCreated);
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@RequestBody ReservationDto reservationDto) {
        bookingService.cancelReservation(reservationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReservation(@RequestBody UpdateReservationDto updateReservationDto) {
        Reservation updatedReservation = bookingService.updateReservation(updateReservationDto);
        ReservationDto reservationDtoUpdated = entityDtoMapper.createReservationDto(updatedReservation);
        return ResponseEntity.ok(reservationDtoUpdated);
    }

    @GetMapping(value = "/table/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTableAvailability(@RequestParam("id") Long restaurantId,
                                                  @RequestParam("capacity") Long capacity,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, List<DiningTable>> tableAvailabilityMap =
                bookingService.findTablesByRestaurantIdAndSeatingCapacityAndDateRange(restaurantId, capacity, startDate, endDate);
        Map<LocalDate, List<DiningTableDto>> tableAvailabilityMapDto =
                entityDtoMapper.getTableAvailabilityDtoMap(tableAvailabilityMap);
        return ResponseEntity.ok(tableAvailabilityMapDto);
    }
}
