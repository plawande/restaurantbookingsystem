package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    List<Restaurant> findAllRestaurants();

    Restaurant findRestaurantById(Long id);

    List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id, Long capacity);

    List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id, LocalDate startDate, LocalDate endDate);

    Map<LocalDate, List<DiningTable>> findTablesByRestaurantIdAndSeatingCapacityAndDateRange(Long id, Long capacity, LocalDate startDate, LocalDate endDate);

    Reservation createNewReservation(ReservationDto reservationDto);

    Reservation cancelReservation(ReservationDto reservationDto);

    Reservation updateReservation(UpdateReservationDto updateReservationDto);

    Map<LocalDate, List<DiningTableDto>> getTableAvailabilityDtoMap(Map<LocalDate, List<DiningTable>> tableAvailabilityMap);
}
