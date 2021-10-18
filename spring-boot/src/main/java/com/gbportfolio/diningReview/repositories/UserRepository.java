package com.gbportfolio.diningReview.repositories;

import java.util.Optional;

import com.gbportfolio.diningReview.entities.*;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    Optional<User> findOptionalByUsername(String username);
    

/*

UserRepository queries idea


1. As an unregistered user, I want to create my user profile using a display name that’s unique only to me.
@PostMapping("/create-user") //USERNAME UNIQUE

you can use "save" for this

2. As a registered user, I want to update my user profile. I cannot modify my unique display name.
@PutMapping("/{username}/profile/update-username") **DISALLOWED**
@PutMapping("/{username}/profile/update-city")
@PutMapping("/{username}/profile/update-state")
@PutMapping("/{username}/profile/update-zipcode")
@PutMapping("/{username}/profile/update-interestpeanut")
@PutMapping("/{username}/profile/update-interestegg")
@PutMapping("/{username}/profile/update-interestdairy")

you can use "findById" for all the adjustments above



3. As an application experience, I want to fetch the user profile belonging to a given display name.
@GetMapping("/{username}/profile")

fetches user's username, city, state, zipcode, interestsinpeanut/egg/dairy by username

User findByUserName(String username)


4. As part of the backend process that validates a user-submitted dining review, I want to verify that the user exists, based on the user display name associated with the dining review.

Optional<User> findOptionalByUserName


*/


}

