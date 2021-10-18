package com.gbportfolio.diningReview.entities;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import com.gbportfolio.diningReview.entities.AdminReviewAction;
import com.gbportfolio.diningReview.enums.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DiningReview {

    @Id
    @GeneratedValue
    private Long id;


    private String name;
    private Long restaurant;

    private Double peanutScore;
    public Optional<Double> getPeanutScore(){
        return Optional.ofNullable(this.peanutScore);
    }

    private Double eggScore;
    public Optional<Double> getEggScore(){
        return Optional.ofNullable(this.eggScore);
    }

    private Double dairyScore;
    public Optional<Double> getDairyScore(){
        return Optional.ofNullable(this.dairyScore);
    }
    
    // @Getter(AccessLevel.NONE)
    private String commentary;
    public Optional<String> getCommentary(){
        return Optional.ofNullable(this.commentary);
    }

    private StatusTypes status = StatusTypes.PENDING;

}
