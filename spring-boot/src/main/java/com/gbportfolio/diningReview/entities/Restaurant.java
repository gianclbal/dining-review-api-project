package com.gbportfolio.diningReview.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {

    // public Integer getAverage(){
    //     Integer average = 
    //     return average;
    // }

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer zipcode;

    @Column(name="PEANUTSCORE")
    protected Double peanutScore;

    @Column(name="EGGSCORE")
    protected Double eggScore;

    @Column(name="DAIRYSCORE")
    protected Double dairyScore;

    @Column(name="OVERALLSCORE")
    private Double overallScore;
    // (this.peanutScore + this.eggScore + this.dairyScore) / 3;

}