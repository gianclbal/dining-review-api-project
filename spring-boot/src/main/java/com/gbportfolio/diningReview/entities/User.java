package com.gbportfolio.diningReview.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String username;

    private String city;
    private String state;
    private Integer zipcode;

    @Column(name="PEANUTALLERGY")
    private boolean peanutAllergy;

    @Column(name="EGGALLERGY")
    private boolean eggAllergy;

    @Column(name="DAIRYALLERGY")
    private boolean dairyAllergy;
    
}
