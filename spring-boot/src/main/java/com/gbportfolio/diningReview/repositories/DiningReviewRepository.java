package com.gbportfolio.diningReview.repositories;

import java.util.List;
import java.util.Optional;

import com.gbportfolio.diningReview.enums.*;
import com.gbportfolio.diningReview.entities.*;

import org.springframework.data.repository.CrudRepository;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Integer> {
    List<DiningReview> findByStatus(StatusTypes status); //handlees both approved and not approved statuses
    Optional<DiningReview> findOptionalById(Long id);
    Optional<Restaurant> findOptionalByRestaurant(Long id);
    List<DiningReview> findByRestaurantAndStatus(Long id, StatusTypes status);
    /*
    As a registered user, I want to submit a dining review.
    can do this with save

As an admin, I want to get the list of all dining reviews that are pending approval.

As an admin, I want to approve or reject a given dining review.

can do this by findById then setStatus(approved)


As part of the backend process that updates a restaurant’s set of scores, I want to fetch the set of all approved dining reviews belonging to this restaurant.

    */

}
