package com.project.restaurantbookingsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateReservationDto {
    private ReservationDto existingReservationDto;
    private ReservationDto newReservationDto;
}
