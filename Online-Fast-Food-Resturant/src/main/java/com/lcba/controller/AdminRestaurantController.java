package com.lcba.controller;


import com.lcba.Request.CreateRestuarantRequest;
import com.lcba.model.Restaurant;
import com.lcba.model.User;
import com.lcba.response.MessageResponse;
import com.lcba.service.RestaurantService;
import com.lcba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurant")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/createRestaurant")
    public ResponseEntity<?> createRestaurant(@RequestBody CreateRestuarantRequest request,
                                              @RequestHeader("Authorization") String jwt) {
        try {
            // Extract the token from the Bearer string
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse());
            }

            String token = jwt.substring(7); // Remove "Bearer " prefix

            // Find user based on the token
            User user = userService.findUserByJwtToken(token);

            // Create restaurant
            Restaurant restaurant = restaurantService.createRestaurant(request, user);

            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse());
        }
    }


    @GetMapping("/getAllRestaurant")
    public ResponseEntity<?> getAllRestaurant(@RequestHeader("Authorization") String jwt) {
        try {
            // Validate Authorization header
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse());
            }

            // Extract token from Bearer string
            String token = jwt.substring(7);

            try {
                // Validate and find user based on the token
                User user = userService.findUserByJwtToken(token);

                // Retrieve all restaurants
                List<Restaurant> restaurants = restaurantService.getAllRestaurant();

                return new ResponseEntity<>(restaurants, HttpStatus.OK);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse());
        }
    }





    @PutMapping("/updateRestaurant/{id}")
    public ResponseEntity<?> updateRestaurant(@RequestBody CreateRestuarantRequest request,
                                              @RequestHeader("Authorization") String jwt,
                                              @PathVariable Long id) {
        try {
            // Validate Authorization header
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse());
            }

            // Extract token from Bearer string
            String token = jwt.substring(7);

            try {
                // Find user based on the token
                User user = userService.findUserByJwtToken(token);

                // Update restaurant
                Restaurant restaurant = restaurantService.updateRestaurant(id, request);

                return new ResponseEntity<>(restaurant, HttpStatus.OK);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse());
        }
    }


    @DeleteMapping("/deleteRestaurant/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id
    ) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse response=new MessageResponse();
        response.setMessage("Restaurant Deleted Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateRestaurantStatus/{id}/status")
    public ResponseEntity<?> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) {
        try {
            // Validate Authorization header
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse());
            }

            // Extract token from Bearer string
            String token = jwt.substring(7);

            try {
                // Validate and find user based on the token
                User user = userService.findUserByJwtToken(token);

                // Update restaurant status
                Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

                return new ResponseEntity<>(restaurant, HttpStatus.OK);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse());
        }
    }


    @GetMapping("/findRestaurantByUserId/{id}")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
                                                             @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }




}
