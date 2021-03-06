package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id, Long capacity);

    List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id, LocalDate startDate, LocalDate endDate);

    Map<LocalDate, List<DiningTable>> findTablesByRestaurantIdAndSeatingCapacityAndDateRange(Long id, Long capacity, LocalDate startDate, LocalDate endDate);

    Reservation getExistingReservation(ReservationDto reservationDto);

    Reservation createNewReservation(ReservationDto reservationDto);

    void cancelReservation(ReservationDto reservationDto);

    Reservation updateReservation(UpdateReservationDto updateReservationDto);
}
