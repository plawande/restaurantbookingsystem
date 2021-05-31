package com.project.restaurantbookingsystem.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.ReservationPk;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TestRunner implements CommandLineRunner {

    @Autowired
    private BookingService bookingService;

    @Override
    public void run(String... args) throws Exception {
        /*List<Restaurant> restaurantList =
                bookingService.findAllRestaurants();

        for(Restaurant r: restaurantList) {
            System.out.println();
        }*/

        /*List<DiningTable> allTableList =
                bookingService.findTablesByRestaurantIdAndSeatingCapacity(1L, 4L);

        for(DiningTable dt: allTableList) {
            System.out.println(dt.getNumber() + " " + dt.getCapacity());
        }*/

        /*LocalDate startDate = LocalDate.of(2021, 05, 29);
        LocalDate endDate = LocalDate.of(2021, 05, 30);

        List<Reservation> reservationList =
                bookingService.findReservationsByRestaurantIdAndDateRange(1L, startDate, endDate);

        for(Reservation rvn: reservationList) {
            System.out.println();
        }*/

        /*LocalDate startDate = LocalDate.of(2021, 5, 29);
        LocalDate endDate = LocalDate.of(2021, 6, 5);
        Map<LocalDate, List<DiningTable>> map =
                bookingService.findTablesByRestaurantIdAndSeatingCapacityAndDateRange(3L, 4L, startDate, endDate);

        for(Map.Entry<LocalDate, List<DiningTable>> entry: map.entrySet()) {
            List<Long> idList = entry.getValue().stream().map(table -> table.getId()).collect(Collectors.toList());
            System.out.println(entry.getKey() + " : " + idList);
        }*/

        //System.out.println(new ObjectMapper().writeValueAsString(map));

        /*Reservation reservation = new Reservation();
        ReservationPk rpk = new ReservationPk();
        rpk.setTableId(10L);
        rpk.setDate(LocalDate.of(2021, 06, 04));
        reservation.setReservationPk(rpk);
        reservation.setStatus("Active");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println(mapper.writeValueAsString(reservation));
        Reservation newReservation = bookingService.createNewReservation(reservation);
        System.out.println("New Table Reserved: " + newReservation.getStatus() + " " +
                newReservation.getReservationPk().getDate() + " " +
                newReservation.getReservationPk().getTableId());*/
    }
}
