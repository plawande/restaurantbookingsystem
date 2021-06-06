package com.project.restaurantbookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {
    private Long tableId;
    private LocalDate date;
    private DiningTableDto diningTableDto;
    private UserDto userDto;
}
