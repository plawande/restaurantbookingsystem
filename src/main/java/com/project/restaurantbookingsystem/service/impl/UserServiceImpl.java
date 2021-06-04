package com.project.restaurantbookingsystem.service.impl;

import com.project.restaurantbookingsystem.dao.UserDao;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UserDto;
import com.project.restaurantbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<ReservationDto> getUserReservationDetails(Long mobileNo) {
        return userDao.getUserReservationDetails(mobileNo);
    }
}
