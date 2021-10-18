package com.gbportfolio.diningReview.repositories;

import java.util.List;
import java.util.Optional;

import com.gbportfolio.diningReview.entities.*;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    // List<Restaurant> findByZipcodeOrderByPeanutScoreOrEggScoreOrDairyScoreByDesc(
    //     Integer zipcode, 
    //     Optional<Integer> peanutScore, 
    //     Optional<Integer> eggScore,
    //     Optional<Integer> dairyScore
    // );
    Boolean existsByNameAndZipcode(String name, Integer zipcode);
    Boolean existsById(Long id);
    Optional<Restaurant> findOptionalById(Long id);
    List<Restaurant> findByZipcode(Integer zipcode);
    List<Restaurant> findByPeanutScoreGreaterThanOrderByPeanutScoreDesc(Double minNum);
    List<Restaurant> findByEggScoreGreaterThanOrderByEggScoreDesc(Double minNum);
    List<Restaurant> findByDairyScoreGreaterThanOrderByDairyScoreDesc(Double minNum);

    List<Restaurant> findByZipcodeAndPeanutScoreGreaterThanOrderByPeanutScoreDesc(Integer zipcode, Double minNum);
    List<Restaurant> findByZipcodeAndEggScoreGreaterThanOrderByEggScoreDesc(Integer zipcode, Double minNum);
    List<Restaurant> findByZipcodeAndDairyScoreGreaterThanOrderByDairyScoreDesc(Integer zipcode, Double minNum);



    /*

As an application experience, I want to submit a new restaurant entry. Should a restaurant with the same name and with the same zip code already exist, I will see a failure.

Unique constraints sample:
@Table(
   name = "product_serial_group_mask", 
   uniqueConstraints = {@UniqueConstraint(columnNames = {"mask", "group"})}
)


As an application experience, I want to fetch the details of a restaurant, given its unique Id.

@GetMapping("/{id}")
fetch restaurant details by restaurant id

use findById


As an application experience, I want to fetch restaurants that match a given zip code and that also have at least one user-submitted score for a given allergy. I want to see them sorted in descending order.
   
 List<Restaurant> findByZipcodeAndDiningScoreGreaterThanOrderByDesc(
        Integer zipcode, 
        Optional<Integer> peanutScore, 
        Optional<Integer> eggScore,
        Optional<Integer> dairyScore
    );

*/

    
}

