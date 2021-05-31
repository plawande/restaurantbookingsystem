package com.project.restaurantbookingsystem.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ReservationPk implements Serializable {
    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "date")
    private LocalDate date;
}
