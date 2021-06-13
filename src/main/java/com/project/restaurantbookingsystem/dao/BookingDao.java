package com.project.restaurantbookingsystem.dao;

import com.project.restaurantbookingsystem.entity.*;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {
    List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id, Long capacity);

    List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id, LocalDate startDate, LocalDate endDate);

    Reservation getExistingReservation(ReservationPk reservationPk);

    Reservation createNewReservation(Reservation reservation);

    void cancelReservation(Reservation reservation);

    ArchivedReservation archiveReservation(ArchivedReservation archivedReservation);
}
