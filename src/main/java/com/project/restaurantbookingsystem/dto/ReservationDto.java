package com.project.restaurantbookingsystem.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationDto {
    private Long tableId;
    private LocalDate date;
    private String status;
}
