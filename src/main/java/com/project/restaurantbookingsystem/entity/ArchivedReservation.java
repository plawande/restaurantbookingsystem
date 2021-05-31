package com.project.restaurantbookingsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ArchivedReservation extends BaseClass {
    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private String status;
}
