package com.project.restaurantbookingsystem.service.impl;

import com.project.restaurantbookingsystem.dao.RestaurantDao;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantDao.findAllRestaurants();
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantDao.findRestaurantById(id);
    }
}
