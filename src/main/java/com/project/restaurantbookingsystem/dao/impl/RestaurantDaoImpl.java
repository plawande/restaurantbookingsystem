package com.project.restaurantbookingsystem.dao.impl;

import com.project.restaurantbookingsystem.dao.RestaurantDao;
import com.project.restaurantbookingsystem.dao.RestaurantRepository;
import com.project.restaurantbookingsystem.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

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
        return entityManager.createQuery("" +
                "Select r " +
                "from Restaurant r " +
                "left join fetch r.tables dt " +
                "where r.id = :id", Restaurant.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
