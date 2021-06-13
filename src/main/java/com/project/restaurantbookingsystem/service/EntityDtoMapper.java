package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.RestaurantDto;
import com.project.restaurantbookingsystem.dto.UserDto;
import com.project.restaurantbookingsystem.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

    public List<RestaurantDto> getRestaurantsDtoList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::createRestaurantDto).collect(Collectors.toList());
    }

    public RestaurantDto createRestaurantDto(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        return restaurantDto;
    }

    public RestaurantDto createRestaurantDtoWithTables(Restaurant restaurant) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        List<DiningTable> tables = restaurant.getTables();
        List<DiningTableDto> diningTableDtos = new ArrayList<>();
        Iterator<DiningTable> tableIterator = tables.iterator();
        while(tableIterator.hasNext()) {
            DiningTable diningTable = tableIterator.next();
            DiningTableDto diningTableDto = new DiningTableDto();
            diningTableDto.setId(diningTable.getId());
            diningTableDto.setNumber(diningTable.getNumber());
            diningTableDto.setCapacity(diningTable.getCapacity());
            diningTableDtos.add(diningTableDto);
        }
        restaurantDto.setDiningTableDtos(diningTableDtos);
        return restaurantDto;
    }

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
        UserDto userDto = reservationDto.getUserDto();
        User user = new User();
        user.setId(Long.valueOf(userDto.getId()));
        reservation.setUser(user);
        return reservation;
    }

    public ReservationDto createReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTableId(reservation.getReservationPk().getTableId());
        reservationDto.setDate(reservation.getReservationPk().getDate());
        User user = reservation.getUser();
        UserDto userDto = new UserDto();
        userDto.setId(String.valueOf(user.getId()));
        reservationDto.setUserDto(userDto);
        return reservationDto;
    }
}
