package com.project.restaurantbookingsystem.service;

import com.project.restaurantbookingsystem.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> findAllRestaurants();

    Restaurant findRestaurantById(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);
}
