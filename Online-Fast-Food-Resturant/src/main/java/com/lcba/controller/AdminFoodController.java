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

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;


    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping("/createFood")
    public ResponseEntity<Food> createFood(
            @RequestBody CreateFoodRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(request.getRestaurantId());
        Food food=foodService.createFood(request,request.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("Food Deleted Successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PutMapping("/updateFoodAvailibity/{id}")
    public ResponseEntity<Food> updateFoodAvailityStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Food food= foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }



}
