package com.project.restaurantbookingsystem.service.impl;

import com.project.restaurantbookingsystem.dao.BookingDao;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.ReservationPk;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return bookingDao.findAllRestaurants();
    }

    @Override
    public List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id,
                                                                        Long capacity) {
        return bookingDao.findTablesByRestaurantIdAndSeatingCapacity(id, capacity);
    }

    @Override
    public List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id,
                                                                        LocalDate startDate,
                                                                        LocalDate endDate) {
        return bookingDao.findReservationsByRestaurantIdAndDateRange(id, startDate, endDate);
    }

    @Override
    public Map<LocalDate, List<DiningTable>> findTablesByRestaurantIdAndSeatingCapacityAndDateRange(Long id,
                                                                                    Long capacity,
                                                                                    LocalDate startDate,
                                                                                    LocalDate endDate) {
        List<DiningTable> allTablesByCapacity =
                findTablesByRestaurantIdAndSeatingCapacity(id, capacity);
        List<Reservation> reservationsByDateRange =
                findReservationsByRestaurantIdAndDateRange(id, startDate, endDate);

        Map<LocalDate, List<Long>> reservationsMap = reservationsByDateRange.stream()
                .collect(Collectors.groupingBy(reservation -> reservation.getReservationPk().getDate(),
                        Collectors.mapping(reservation -> reservation.getTable().getId(), Collectors.toList())));

        List<LocalDate> dates = getAllDatesInRange(startDate, endDate);
        Map<LocalDate, List<DiningTable>> nonReservedMap = new LinkedHashMap<>();

        for(LocalDate date: dates) {
            List<Long> bookedTableIds = reservationsMap.get(date);
            if(bookedTableIds != null) {
                nonReservedMap.put(date, new ArrayList<>());
                allTablesByCapacity.stream()
                        .filter(diningTable -> !bookedTableIds.contains(diningTable.getId()))
                        .map(diningTable -> {
                            List<DiningTable> list = nonReservedMap.get(date);
                            return list.add(diningTable);
                        })
                        .collect(Collectors.toList());
            } else {
                nonReservedMap.put(date, allTablesByCapacity);
            }
        }

        return nonReservedMap;
    }

    @Override
    public Reservation createNewReservation(Reservation reservation) {
        return bookingDao.createNewReservation(reservation);
    }

    @Override
    public Reservation cancelReservation(Reservation reservation) {
        return bookingDao.cancelReservation(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservation(UpdateReservationDto updateReservationDto) {
        Map<String, Reservation> reservationMap =
                createReservationEntities(updateReservationDto);
        bookingDao.cancelReservation(reservationMap.get("existing"));
        return bookingDao.createNewReservation(reservationMap.get("new"));
    }

    private Map<String, Reservation> createReservationEntities(UpdateReservationDto updateReservationDto) {
        Reservation existingReservation =
                createReservationEntity(updateReservationDto.getExistingReservationDto());
        Reservation newReservation =
                createReservationEntity(updateReservationDto.getNewReservationDto());
        Map<String, Reservation> reservationMap = new HashMap<>();
        reservationMap.put("existing", existingReservation);
        reservationMap.put("new", newReservation);
        return reservationMap;
    }

    private Reservation createReservationEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        ReservationPk reservationPk = new ReservationPk();
        reservationPk.setTableId(reservationDto.getTableId());
        reservationPk.setDate(reservationDto.getDate());
        reservation.setReservationPk(reservationPk);
        reservation.setStatus(reservationDto.getStatus());
        return reservation;
    }

    private List<LocalDate> getAllDatesInRange(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        int days = period.getDays();
        List<LocalDate> dates = new ArrayList<>();
        for(int i = 0; i <= days; i++) {
            if(i != 0) {
                LocalDate localDate = dates.get(i -1);
                dates.add(localDate.plusDays(1));
            } else {
                dates.add(startDate);
            }
        }
        return dates;
    }
}
