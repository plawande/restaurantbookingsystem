package com.project.restaurantbookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateReservationDto {
    private ReservationDto existingReservationDto;
    private ReservationDto newReservationDto;
}
