package com.project.restaurantbookingsystem.dao.impl;

import com.project.restaurantbookingsystem.dao.BookingDao;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.Restaurant;
import org.hibernate.annotations.QueryHints;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class BookingDaoImpl implements BookingDao {

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

    @Override
    @Transactional
    public List<DiningTable> findTablesByRestaurantIdAndSeatingCapacity(Long id,
                                                                        Long capacity) {
        return entityManager.createQuery("" +
                "Select dt " +
                "from DiningTable dt " +
                "where " +
                "dt.restaurant.id = :id " +
                "and dt.capacity = :capacity", DiningTable.class)
                .setParameter("id", id)
                .setParameter("capacity", capacity)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Reservation> findReservationsByRestaurantIdAndDateRange(Long id,
                                                                        LocalDate startDate,
                                                                        LocalDate endDate) {
        return entityManager.createQuery("" +
                "Select distinct rvn " +
                "from Reservation rvn " +
                "join fetch rvn.table dt " +
                "where " +
                "dt.restaurant.id = :id " +
                "and rvn.reservationPk.date between :startDate and :endDate", Reservation.class)
                .setParameter("id", id)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
    }

    @Override
    @Transactional
    public Reservation createNewReservation(Reservation reservation) {
        entityManager.persist(reservation);
        return reservation;
    }

    @Override
    @Transactional
    public Reservation cancelReservation(Reservation reservation) {
        return entityManager.merge(reservation);
    }
}

//https://www.baeldung.com/spring-dataIntegrityviolationexception
//https://stackoverflow.com/questions/30088649/how-to-use-multiple-join-fetch-in-one-jpql-query
//https://vladmihalcea.com/jpa-persist-merge-hibernate-save-update-saveorupdate/
//https://stackoverflow.com/questions/1607532/when-to-use-entitymanager-find-vs-entitymanager-getreference-with-jpa
