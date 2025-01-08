package com.lcba.controller;


import com.lcba.dto.RestrurantDto;
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
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
                                                        @RequestParam String keyword,
                                                       @RequestHeader("Authorization") String jwt
                                                        ) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant=restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }



    @GetMapping("/findRestaurantById/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @PutMapping("/addToFavorites/{id}")
    public ResponseEntity<?> addToFavorites(
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

                // Add restaurant to favorites
                RestrurantDto restaurant = restaurantService.addToFavorites(id, user);

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



}
