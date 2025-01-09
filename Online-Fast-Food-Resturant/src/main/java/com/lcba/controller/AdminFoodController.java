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
    public ResponseEntity<?> createFood(
            @RequestBody CreateFoodRequest request,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            // Validate Authorization header
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid or missing Authorization header"));
            }

            // Extract token from Bearer string
            String token = jwt.substring(7);

            try {
                // Validate and find user based on the token
                User user = userService.findUserByJwtToken(token);

                // Find restaurant
                Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

                // Create food
                Food food = foodService.createFood(request, request.getCategory(), restaurant);

                return new ResponseEntity<>(food, HttpStatus.CREATED);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Invalid token format: " + e.getMessage()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error creating food: " + e.getMessage()));
        }
    }


    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse("Invalid or missing Authorization header");
        res.setMessage("Food Deleted Successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PutMapping("/updateFoodAvailibity/{id}")
    public ResponseEntity<?> updateFoodAvailityStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            // Validate Authorization header
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid or missing Authorization header"));
            }

            // Extract token from Bearer string
            String token = jwt.substring(7);

            try {
                // Validate and find user based on the token
                User user = userService.findUserByJwtToken(token);

                // Update food availability status
                Food food = foodService.updateAvailibilityStatus(id);

                return new ResponseEntity<>(food, HttpStatus.CREATED);
            } catch (io.jsonwebtoken.io.DecodingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Invalid token format: " + e.getMessage()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error updating food availability status: " + e.getMessage()));
        }
    }

    @GetMapping("/findFood/{id}")
    public ResponseEntity<?> findFoodById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
        try {
            // Extract the token from the Bearer string
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Invalid or missing Authorization header"));
            }

            String token = jwt.substring(7); // Remove "Bearer " prefix

            // Find user based on the token
            User user = userService.findUserByJwtToken(token);

            // Retrieve the food item by ID
            Food food = foodService.findFoodById(id);

            return new ResponseEntity<>(food, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error finding food: " + e.getMessage()));
        }
    }


}
