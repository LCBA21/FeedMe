package com.lcba.controller;


import com.lcba.Request.CreateFoodRequest;
import com.lcba.model.Food;
import com.lcba.model.Restaurant;
import com.lcba.model.User;
import com.lcba.response.MessageResponse;
import com.lcba.service.FoodService;
import com.lcba.service.RestaurantService;
import com.lcba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String name,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.searchFood(name);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vagetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.getRestaurantsFood(restaurantId,vagetarian,nonVeg,seasonal,food_category);


        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PutMapping("/deleteFood/{id}")
    public ResponseEntity<Food> updateFoodAvailityStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
       Food food= foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }








}
