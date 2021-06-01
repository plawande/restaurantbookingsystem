package com.project.restaurantbookingsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiningTableDto {
    private Long id;
    private Long number;
    private Long capacity;
}
