package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.ReservationPk;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

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

    public DiningTableDto mapToDiningTableDto(DiningTable diningTable) {
        DiningTableDto diningTableDto = new DiningTableDto();
        diningTableDto.setId(diningTable.getId());
        diningTableDto.setNumber(diningTable.getNumber());
        diningTableDto.setCapacity(diningTable.getCapacity());
        return diningTableDto;
    }

    public Reservation createReservationEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        ReservationPk reservationPk = new ReservationPk();
        reservationPk.setTableId(reservationDto.getTableId());
        reservationPk.setDate(reservationDto.getDate());
        reservation.setReservationPk(reservationPk);
        return reservation;
    }

    public ReservationDto createReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTableId(reservation.getReservationPk().getTableId());
        reservationDto.setDate(reservation.getReservationPk().getDate());
        return reservationDto;
    }
}
