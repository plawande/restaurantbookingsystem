package com.project.restaurantbookingsystem.dao.impl;

import com.project.restaurantbookingsystem.dao.RestaurantDao;
import com.project.restaurantbookingsystem.entity.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Restaurant> findAllRestaurants() {
        return entityManager.createQuery("" +
                "Select r " +
                "from Restaurant r ", Restaurant.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Restaurant findRestaurantById(Long id) {
        return entityManager.find(Restaurant.class, id);
    }
}
