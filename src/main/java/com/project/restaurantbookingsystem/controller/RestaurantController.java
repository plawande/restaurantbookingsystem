package com.project.restaurantbookingsystem.controller;

import com.project.restaurantbookingsystem.dto.RestaurantDto;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.EntityDtoMapper;
import com.project.restaurantbookingsystem.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private EntityDtoMapper entityDtoMapper;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        List<RestaurantDto> restaurantsDtoList = entityDtoMapper.getRestaurantsDtoList(restaurants);
        return ResponseEntity.ok(restaurantsDtoList);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        RestaurantDto restaurantsDto = entityDtoMapper.createRestaurantDto(restaurant);
        return ResponseEntity.ok(restaurantsDto);
    }
}
