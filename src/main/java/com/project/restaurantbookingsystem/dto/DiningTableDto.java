package com.project.restaurantbookingsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiningTableDto {
    private Long id;
    private Long number;
    private Long capacity;
    private RestaurantDto restaurantDto;
}
