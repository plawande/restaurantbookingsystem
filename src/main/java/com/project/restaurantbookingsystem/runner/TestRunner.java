package com.project.restaurantbookingsystem.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.restaurantbookingsystem.dto.ReservationDto;
import com.project.restaurantbookingsystem.dto.UserDto;
import com.project.restaurantbookingsystem.entity.DiningTable;
import com.project.restaurantbookingsystem.entity.Reservation;
import com.project.restaurantbookingsystem.entity.ReservationPk;
import com.project.restaurantbookingsystem.entity.Restaurant;
import com.project.restaurantbookingsystem.service.BookingService;
import com.project.restaurantbookingsystem.service.RestaurantService;
import com.project.restaurantbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TestRunner implements CommandLineRunner {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

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

        /*List<ReservationDto> reservationDtos =
                userService.getUserReservationDetails(9545925726L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println(mapper.writeValueAsString(reservationDtos));*/

        /*ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTableId(1L);
        reservationDto.setDate(LocalDate.of(2021, 05, 29));
        Reservation reservation =
                bookingService.getExistingReservation(reservationDto);
        System.out.println();*/

        //Persist operation
        /*Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        DiningTable diningTable = new DiningTable();
        diningTable.setNumber(21L);
        diningTable.setCapacity(6L);
        diningTable.setRestaurant(restaurant);
        DiningTable diningTable2 = new DiningTable();
        diningTable2.setNumber(22L);
        diningTable2.setCapacity(6L);
        diningTable2.setRestaurant(restaurant);
        List<DiningTable> diningTables = new ArrayList<>();
        diningTables.add(diningTable);
        diningTables.add(diningTable2);
        restaurant.setTables(diningTables);
        restaurantService.saveRestaurant(restaurant);*/

        //Merge operation
        /*Restaurant restaurant = restaurantService.findRestaurantById(8L);
        List<DiningTable> diningTableList = restaurant.getTables();
        List<Long> removeIds = Arrays.asList(24L);
        List<DiningTable> diningTableListToBeMerged =diningTableList.stream()
                .filter(diningTable -> !removeIds.contains(diningTable.getId()))  //delete orphan
                .collect(Collectors.toList());
        for(DiningTable diningTable: diningTableList) {
            if(diningTable.getId().equals(15L))
                diningTable.setCapacity(9L);  //merge
        }
        DiningTable diningTable = new DiningTable();
        diningTable.setNumber(35L);
        diningTable.setCapacity(6L);
        diningTable.setRestaurant(restaurant);
        diningTableListToBeMerged.add(diningTable);  //persist
        restaurant.setTables(diningTableListToBeMerged);
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(restaurant);
        System.out.println(updatedRestaurant.getTables().size());*/
    }
}
