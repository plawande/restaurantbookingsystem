package com.project.restaurantbookingsystem.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseClass {
    @Column(name = "name")
    private String name;

    @NaturalId
    @Column(name = "mobile_no")
    private Long mobileNo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
