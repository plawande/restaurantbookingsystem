package com.project.restaurantbookingsystem.dao;

import com.project.restaurantbookingsystem.entity.Restaurant;

import java.util.List;

public interface RestaurantDao {
    List<Restaurant> findAllRestaurants();

    Restaurant findRestaurantById(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);
}
