package com.project.restaurantbookingsystem.dao.impl;

import com.project.restaurantbookingsystem.dao.UserDao;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UserDto;
import com.project.restaurantbookingsystem.service.UserReservationDtoTransformer;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReservationDto> getUserReservationDetails(Long mobileNo) {
        List<ReservationDto> reservationDtos =
                entityManager.createQuery("" +
                "Select rvn.reservationPk.tableId as rvn_dt_id, " +
                "rvn.reservationPk.date as rvn_date, " +
                "dt.number as dt_no, " +
                "rstrnt.name as rstrnt_name " +
                "from User u " +
                "join u.reservations rvn " +
                "join rvn.table dt " +
                "join dt.restaurant rstrnt " +
                "where u.mobileNo = :mobileNo")
                .unwrap(Query.class)
                .setResultTransformer(new UserReservationDtoTransformer())
                .setParameter("mobileNo", mobileNo)
                .getResultList();

        return reservationDtos;
    }
}
