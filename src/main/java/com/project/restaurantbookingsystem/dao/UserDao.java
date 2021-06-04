package com.project.restaurantbookingsystem.dao;

import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UserDto;

import java.util.List;

public interface UserDao {
    List<ReservationDto> getUserReservationDetails(Long mobileNo);
}
