package com.project.restaurantbookingsystem.dao.impl;

import com.project.restaurantbookingsystem.dao.BookingDao;
import com.project.restaurantbookingsystem.entity.*;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Reservation getExistingReservation(ReservationPk reservationPk) {
        List<Reservation> list = entityManager.createQuery("" +
                "Select rvn " +
                "from Reservation rvn " +
                "join fetch rvn.user usr " +
                "where rvn.reservationPk = :reservationPk")
                .setParameter("reservationPk", reservationPk)
                .getResultList();
        return CollectionUtils.isEmpty(list) ? null : list.get(0);  //better exception handling needed
    }


    @Override
    @Transactional
    public Reservation createNewReservation(Reservation reservation) {
        entityManager.persist(reservation);
        return reservation;
    }

    @Override
    @Transactional
    public void cancelReservation(Reservation reservation) {
        /*Reservation reservationPersistent =
                entityManager.find(Reservation.class, reservation.getReservationPk());
        entityManager.remove(reservationPersistent);*/ //This commented section is the way to go if the incoming entity is not persistent.
        entityManager.remove(reservation);
    }

    @Override
    @Transactional
    public ArchivedReservation archiveReservation(ArchivedReservation archivedReservation) {
        entityManager.persist(archivedReservation);
        return archivedReservation;
    }
}

//https://www.baeldung.com/spring-dataIntegrityviolationexception
//https://stackoverflow.com/questions/30088649/how-to-use-multiple-join-fetch-in-one-jpql-query
//https://vladmihalcea.com/jpa-persist-merge-hibernate-save-update-saveorupdate/
//https://stackoverflow.com/questions/1607532/when-to-use-entitymanager-find-vs-entitymanager-getreference-with-jpa
//https://stackoverflow.com/questions/11539720/javax-persistence-entitymanager-remove-method/11539796
//https://www.baeldung.com/jpa-entity-manager-get-reference