package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.DiningTableDto;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.RestaurantDto;
import com.project.restaurantbookingsystem.dto.UserDto;
import org.hibernate.transform.ResultTransformer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReservationDtoTransformer implements ResultTransformer {

    private final String reservationTableId = "rvn_dt_id";
    private final String reservationDate = "rvn_date";
    private final String tableNo = "dt_no";
    private final String restaurantName = "rstrnt_name";

    @Override
    public Object transformTuple(Object[] values, String[] aliases) {
        Map<String, Object> map = fillMapWithAliasesAndSpecificValues(aliases, values);
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTableId((Long) map.get(reservationTableId));
        reservationDto.setDate(LocalDate.parse(String.valueOf(map.get(reservationDate))));
        DiningTableDto diningTableDto = new DiningTableDto();
        diningTableDto.setNumber((Long) map.get(tableNo));
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(String.valueOf(map.get(restaurantName)));
        diningTableDto.setRestaurantDto(restaurantDto);
        reservationDto.setDiningTableDto(diningTableDto);

        return reservationDto;
    }

    private Map<String, Object> fillMapWithAliasesAndSpecificValues(String[] aliases, Object[] values) {
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i < aliases.length; i++) {
            map.put(aliases[i], values[i]);
        }
        return map;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}
