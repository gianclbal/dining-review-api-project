package com.gbportfolio.diningReview.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.gbportfolio.diningReview.entities.*;
import com.gbportfolio.diningReview.enums.StatusTypes;
import com.gbportfolio.diningReview.repositories.*;
import com.gbportfolio.diningReview.exceptions.*;

@RestController
@RequestMapping("/restaurants")
public class DiningReviewController {

    private RestaurantRepository restaurantRepository;
    private DiningReviewRepository diningReviewRepository;
    private UserRepository userRepository;

    public DiningReviewController(RestaurantRepository restaurantRepository,
            DiningReviewRepository diningReviewRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Restaurant> getAllRestaurants() {
        return this.restaurantRepository.findAll();
    }

    @PostMapping("/add")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        if (this.restaurantRepository.existsByNameAndZipcode(restaurant.getName(), restaurant.getZipcode())) {
            System.out.println("Restaurant already exists in the database!");
            return null;
        }

        Restaurant newRestaurant = this.restaurantRepository.save(restaurant);

        return newRestaurant;
    }

    //NEW
    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") Long id){
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findOptionalById(id);
        if(!restaurantOptional.isPresent()){
            System.out.println("Restaurant does not exist.");
            return null;
        }

        Restaurant restaurant = restaurantOptional.get();
        return restaurant;
    }

    @PutMapping("/user/{username}")
    public User getUser(@PathVariable("username") String username){
        Optional<User> userOptional = this.userRepository.findOptionalByUsername(username);
        if(!userOptional.isPresent()){
            System.out.println("User does not exist.");
            return null;
        }

        User user = userOptional.get();
        return user;
    }

    //Edit profile
    @PutMapping("/user/{username}/edit")
    public User updateUser(@PathVariable("username") String username,
        @RequestParam(required=false) String newUsername, 
        @RequestParam(required=false) String newCity,
        @RequestParam(required=false) String newState,
        @RequestParam(required=false) Integer newZipcode,
        @RequestParam(required=false) Boolean newPeanutAllergy,
        @RequestParam(required=false) Boolean newEggAllergy,
        @RequestParam(required=false) Boolean newDairyAllergy
    ){
        Optional<User> userOptional = this.userRepository.findOptionalByUsername(username);
        if(!userOptional.isPresent()){
            System.out.println("User does not exist.");
            return null;
        }

        User userToBeUpdated = userOptional.get();

        if(newUsername != null){
            System.out.println("Sorry, you can't modify your unique name.");
            return null;

        }
        if(newCity != null){
            userToBeUpdated.setCity(newCity);
        }

        if(newState != null){
            userToBeUpdated.setState(newState);
        }

        if(newZipcode != null){
            userToBeUpdated.setZipcode(newZipcode);
        }

        if(newPeanutAllergy != null){
            userToBeUpdated.setPeanutAllergy(newPeanutAllergy);
        }

        if(newEggAllergy != null){
            userToBeUpdated.setEggAllergy(newEggAllergy);
        }

        if(newDairyAllergy != null){
            userToBeUpdated.setDairyAllergy(newDairyAllergy);
        }

        User userUpdated = this.userRepository.save(userToBeUpdated);

        return userUpdated;
    }


    @GetMapping("/")
    public Iterable<Restaurant> getRestaurantBy(@RequestParam(required=false) Integer zipcode, @RequestParam(required=false) String allergyCategory){
        if(allergyCategory != null && zipcode != null){
            if(allergyCategory.toLowerCase().equals("peanut")){
                return this.restaurantRepository.findByZipcodeAndPeanutScoreGreaterThanOrderByPeanutScoreDesc(zipcode, 1.0);
            } else if (allergyCategory.toLowerCase().equals("egg")){
                return this.restaurantRepository.findByZipcodeAndEggScoreGreaterThanOrderByEggScoreDesc(zipcode, 1.0);
            } else if (allergyCategory.toLowerCase().equals("dairy")){
                return this.restaurantRepository.findByZipcodeAndDairyScoreGreaterThanOrderByDairyScoreDesc(zipcode, 1.0);
            } else {
                System.out.println("No restaurants with zipcode and allergy score for the category you selected found.");
                return null;
            }

        } else if (allergyCategory == null && zipcode != null){
            if(zipcode.toString().length() != 5){
                System.out.println("Invalid zip code");
            } else {
                return this.restaurantRepository.findByZipcode(zipcode);
            }
            return this.restaurantRepository.findByZipcode(zipcode);
        } else if (allergyCategory != null && zipcode == null){
            if(allergyCategory.toLowerCase().equals("peanut")){
                return this.restaurantRepository.findByPeanutScoreGreaterThanOrderByPeanutScoreDesc(1.0);
            } else if (allergyCategory.toLowerCase().equals("egg")){
                return this.restaurantRepository.findByEggScoreGreaterThanOrderByEggScoreDesc(1.0);
            } else if (allergyCategory.toLowerCase().equals("dairy")){
                return this.restaurantRepository.findByDairyScoreGreaterThanOrderByDairyScoreDesc(1.0);
            } else {
                System.out.println("No restaurants with allergy score for the category you selected found.");
                return null;
            }
        }
        else {
            return this.restaurantRepository.findAll();
        }
       
    } 


    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) throws UsernameAlreadyExistsException {
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists. Try a different username.");
        }
        User newUser = this.userRepository.save(user);

        return newUser;
    }

    @PostMapping("/post-review/{username}")
    public DiningReview postReview(@PathVariable("username") String username,
            @RequestBody DiningReview diningReview) {
        Optional<User> userToCheck = this.userRepository.findOptionalByUsername(username);
        List<Double> scores = Arrays.asList(1.0,2.0,3.0,4.0,5.0);

        if (!userToCheck.isPresent()) {
            System.out.println("User not found. You need to create a user before adding a review.");
            return null;
        }

        //Check if restaurant exists
        Optional<Restaurant> restaurantToCheck = this.restaurantRepository.findOptionalById(diningReview.getRestaurant());
        if (!restaurantToCheck.isPresent()) {
            System.out.println("Restaurant does not exist. Cannot create review.");
            return null;
        } 
        
        
        
    
        DiningReview newDiningReview =  this.diningReviewRepository.save(diningReview);
        return newDiningReview;
    


    }

    @GetMapping("/reviews/admin")
    public Iterable<DiningReview> adminGetAllReviews(@RequestParam(required = false, name = "filterBy") String status) {
        if (Objects.nonNull(status)) {
            switch (status) {
            case "APPROVED":
                return this.diningReviewRepository.findByStatus(StatusTypes.APPROVED);
            case "DECLINED":
                return this.diningReviewRepository.findByStatus(StatusTypes.DECLINED);
            case "PENDING":
                return this.diningReviewRepository.findByStatus(StatusTypes.PENDING);
            default:
                return this.diningReviewRepository.findAll();
            }

        }
        return this.diningReviewRepository.findAll();
    }

    @GetMapping("/id={id}/reviews")
    public Iterable<DiningReview> getAllReviews(@PathVariable("id") Long restaurantId) {
        // Check if restaurant is present in db.
        Optional<Restaurant> restaurantOptional = this.restaurantRepository
                .findOptionalById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            System.out.println("Restaurant does not exist. Cannot approve review.");
            return null;
        }

        return this.diningReviewRepository.findByRestaurantAndStatus(restaurantId, StatusTypes.APPROVED);
    }


    private Restaurant calculateOnApprovedScores(Restaurant restaurantBeingReviewed, DiningReview diningReviewApproved)
            throws Exception {
        // Get current scores from db
        Double currentPeanutScore = restaurantBeingReviewed.getPeanutScore();
        Double currentEggScore = restaurantBeingReviewed.getEggScore();
        Double currentDairyScore = restaurantBeingReviewed.getDairyScore();
        Double currentOverallScore = restaurantBeingReviewed.getOverallScore();

        // Get the new scores from the dining review.
        Optional<Double> newPeanutScoreOptional = diningReviewApproved.getPeanutScore();
        Optional<Double> newEggScoreOptional = diningReviewApproved.getEggScore();
        Optional<Double> newDairyScoreOptional = diningReviewApproved.getDairyScore();

        // New instances
        Double newAveragePeanutScore;
        Double newAverageDairyScore;
        Double newAverageEggScore;
        Double newAverageTotalScore;
        Double newAverageOverallScore;

        if (newPeanutScoreOptional.isPresent()){
            Double newPeanutScore = newPeanutScoreOptional.get();

            if(currentPeanutScore != null){
                newAveragePeanutScore = (newPeanutScore + currentPeanutScore) / 2 ;
            } else {
                newAveragePeanutScore = newPeanutScore;
            }
            
            restaurantBeingReviewed.setPeanutScore(newAveragePeanutScore);
        }

        if (newEggScoreOptional.isPresent()){
            Double newEggScore = newEggScoreOptional.get();

            if(currentEggScore != null){
                newAverageEggScore = (newEggScore + currentEggScore) / 2 ;
            } else {
                newAverageEggScore = newEggScore;
            }
            
            restaurantBeingReviewed.setEggScore(newAverageEggScore);
        }
      
        if (newDairyScoreOptional.isPresent()){
            Double newDairycore = newDairyScoreOptional.get();

            if(currentDairyScore != null){
                newAverageDairyScore = (newDairycore + currentDairyScore) / 2 ;
            } else {
                newAverageDairyScore = newDairycore;
            }
            restaurantBeingReviewed.setDairyScore(newAverageDairyScore);
        }

        ///Calculate newAverageOverall
        if (newPeanutScoreOptional.isPresent()) {
            ///1
            //Get the new peanutScore
            Double newPeanutScore = newPeanutScoreOptional.get();
            
            // ///2
            // //Calculate average peanutScore
            // if(currentPeanutScore != null){
            //     newAveragePeanutScore = (newPeanutScore + currentPeanutScore) / 2 ;
            // } else {
            //     newAveragePeanutScore = newPeanutScore;
            // }
            
            // restaurantBeingReviewed.setPeanutScore(newAveragePeanutScore);

            ///3
            //Calculate the average overall new score and set score
            //if new peanutScore, eggScore, and dairyScore are present, the new averrage is the sum of these 3 divided by 3
            if (newEggScoreOptional.isPresent() && newDairyScoreOptional.isPresent()) {
                //Get the new Egg scores
                Double newEggScore = newEggScoreOptional.get();
                Double newDairyScore = newDairyScoreOptional.get();
                newAverageOverallScore = (newPeanutScore + newEggScore + newDairyScore) / 3;

            //if peanutScore and dairyScore are present, the new average is the sum of these 2 divded by 2
            } else if (!newEggScoreOptional.isPresent() && newDairyScoreOptional.isPresent()) {
                Double newDairyScore = newDairyScoreOptional.get();
                newAverageOverallScore = (newPeanutScore + newDairyScore) / 2;
            
            //if peanutScore and eggScore are present, the new average is the sum of these 2 divided by 2
            } else if (!newDairyScoreOptional.isPresent() && newEggScoreOptional.isPresent()) {
                Double newEggScore = newEggScoreOptional.get();
                newAverageOverallScore = (newEggScore + newPeanutScore) / 2;

            //else if peanutScore is not present, newAverage peanut is the new peanut score, and the overall score is new peanut
            } else {
                newAverageOverallScore = newPeanutScore;
            }

            // restaurantBeingReviewed.setOverallScore(newAverageOverallScore);
        //if eggScore is present
        } else if (newEggScoreOptional.isPresent()) {
            ///1
            Double newEggScore = newEggScoreOptional.get();

            // ///2
            // //Calculate average new eggScore and set eggScore in the restaurant entity
            // if(currentEggScore != null) {
            //     newAverageEggScore = (newEggScore + currentEggScore) / 2;
            // } else {
            //     newAverageEggScore = newEggScore;
            // }

            // restaurantBeingReviewed.setEggScore(newAverageEggScore);

            ///3
            //Calculate new average overall average scenarios and set overall score
            if (!newPeanutScoreOptional.isPresent() && newDairyScoreOptional.isPresent()) {
                Double newDairyScore = newDairyScoreOptional.get();
                newAverageOverallScore = (newEggScore + newDairyScore) / 2;
            } else {
                newAverageOverallScore = newEggScore;
            }

            // restaurantBeingReviewed.setOverallScore(newAverageOverallScore);
        //else if newDairy is present (egg and peanut are not...)
        } else if (newDairyScoreOptional.isPresent()) {
            ///1
            Double newDairyScore = newDairyScoreOptional.get();

            // ///2
            // //Calculate newDairyAverage and set
            // if(currentDairyScore != null) {
            //     newAverageDairyScore = (newDairyScore + currentDairyScore) / 2; 
            // } else {
            //     newAverageDairyScore = newDairyScore; 
            // }

            // restaurantBeingReviewed.setDairyScore(newDairyScore);

            ///3
            //Calculate new overall score ave
            //new overall Score aVerage
            newAverageOverallScore = (newDairyScore) / 1;
            // restaurantBeingReviewed.setOverallScore(newAverageOverallScore);
        } else {
            // Nothing new to add...
            newAverageOverallScore = null;
        }

        ///Calculate totalScore
        if(currentOverallScore != null && newAverageOverallScore != null){
            newAverageTotalScore = (newAverageOverallScore + currentOverallScore) / 2;
        } else if (currentOverallScore == null && newAverageOverallScore != null){
            newAverageTotalScore = newAverageOverallScore;
        } else if (currentOverallScore != null && newAverageOverallScore == null){
            newAverageTotalScore = currentOverallScore;
        } else {
            newAverageTotalScore = null;
        }

        restaurantBeingReviewed.setOverallScore(newAverageTotalScore);
        
        Restaurant restaurantReviewCompleted = this.restaurantRepository.save(restaurantBeingReviewed);
        return restaurantReviewCompleted;
    }

    @PutMapping("/reviews/admin/{id}")
    public DiningReview adminReview(@PathVariable("id") Long id, @RequestBody AdminReviewAction adminReviewAction)
            throws Exception {
        // Check if dining review is present in db.
        Optional<DiningReview> diningReviewToReviewOptional = this.diningReviewRepository.findOptionalById(id);
        if (!diningReviewToReviewOptional.isPresent()) {
            System.out.println("Dining review not found. Please try again");
            return null;
        }

        DiningReview diningReviewToReview = diningReviewToReviewOptional.get();

        // Check if restaurant is present in db.
        Optional<Restaurant> restaurantOptional = this.restaurantRepository
                .findOptionalById(diningReviewToReview.getRestaurant());
        if (!restaurantOptional.isPresent()) {
            System.out.println("Restaurant does not exist. Cannot approve review.");
            return null;
        }

        // Change status of this dining review to approved.

        if (adminReviewAction.isReviewAccepted()) {
            diningReviewToReview.setStatus(StatusTypes.APPROVED);
            DiningReview diningReviewApproved = this.diningReviewRepository.save(diningReviewToReview);

            Restaurant restaurantBeingReviewed = restaurantOptional.get();
            calculateOnApprovedScores(restaurantBeingReviewed, diningReviewApproved);
            return diningReviewApproved;

        } else {
            diningReviewToReview.setStatus(StatusTypes.DECLINED);
            DiningReview diningReviewDeclined = this.diningReviewRepository.save(diningReviewToReview);
            return diningReviewDeclined;
        }

    }

}
