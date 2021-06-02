package com.project.restaurantbookingsystem.service.impl;

import com.project.restaurantbookingsystem.dao.BookingDao;
import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UpdateReservationDto;
import com.project.restaurantbookingsystem.entity.*;
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
    public Restaurant findRestaurantById(Long id) {
        return bookingDao.findRestaurantById(id);
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
                List<DiningTable> diningTableList = allTablesByCapacity.stream()
                        .filter(diningTable -> !bookedTableIds.contains(diningTable.getId()))
                        .collect(Collectors.toList());
                nonReservedMap.put(date, diningTableList);
            } else {
                nonReservedMap.put(date, allTablesByCapacity);
            }
        }

        return nonReservedMap;
    }

    @Override
    public Reservation createNewReservation(ReservationDto reservationDto) {
        Reservation reservation = createReservationEntity(reservationDto);
        return bookingDao.createNewReservation(reservation);
    }

    @Override
    @Transactional
    public void cancelReservation(ReservationDto reservationDto) {
        Reservation reservation = createReservationEntity(reservationDto);
        ArchivedReservation archivedReservation = createReservationArchive(reservation);
        archivedReservation.setStatus(BookingStatus.CANCELLED);
        bookingDao.archiveReservation(archivedReservation);
        bookingDao.cancelReservation(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservation(UpdateReservationDto updateReservationDto) {
        Map<String, Reservation> reservationMap =
                createReservationEntities(updateReservationDto);
        Reservation existingReservation = reservationMap.get("existing");
        ArchivedReservation archivedReservation = createReservationArchive(existingReservation);
        archivedReservation.setStatus(BookingStatus.UPDATED);
        bookingDao.archiveReservation(archivedReservation);
        bookingDao.cancelReservation(existingReservation);
        return bookingDao.createNewReservation(reservationMap.get("new"));
    }

    private ArchivedReservation createReservationArchive(Reservation existingReservation) {
        ArchivedReservation archivedReservation = new ArchivedReservation();
        archivedReservation.setTableId(existingReservation.getReservationPk().getTableId());
        archivedReservation.setDate(existingReservation.getReservationPk().getDate());
        return archivedReservation;
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

    @Override
    public Map<LocalDate, List<DiningTableDto>> getTableAvailabilityDtoMap(Map<LocalDate, List<DiningTable>> tableAvailabilityMap) {
        Map<LocalDate, List<DiningTableDto>> diningTableDtoMap = new LinkedHashMap<>();
        for(Map.Entry<LocalDate, List<DiningTable>> entry: tableAvailabilityMap.entrySet()) {
            List<DiningTable> entityList = entry.getValue();
            List<DiningTableDto> entityDtoList =
                    entityList.stream().map(this::mapToDiningTableDto).collect(Collectors.toList());
            diningTableDtoMap.put(entry.getKey(), entityDtoList);
        }
        return diningTableDtoMap;
    }

    private DiningTableDto mapToDiningTableDto(DiningTable diningTable) {
        DiningTableDto diningTableDto = new DiningTableDto();
        diningTableDto.setId(diningTable.getId());
        diningTableDto.setNumber(diningTable.getNumber());
        diningTableDto.setCapacity(diningTable.getCapacity());
        return diningTableDto;
    }
}
