package com.project.restaurantbookingsystem.dao;

import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {
    List<Restaurant> findAllRestaurants();

    Restaurant findRestaurantById(Long id);

    List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id, Long capacity);

    List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id, LocalDate startDate, LocalDate endDate);

    Reservation createNewReservation(Reservation reservation);

    Reservation cancelReservation(Reservation reservation);
}
