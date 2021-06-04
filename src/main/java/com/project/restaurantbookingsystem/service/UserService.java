package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UserDto;

import java.util.List;

public interface UserService {
    List<ReservationDto> getUserReservationDetails(Long mobileNo);
}
