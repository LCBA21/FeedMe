package com.lcba.service;

import com.lcba.Request.CreateFoodRequest;
import com.lcba.model.Category;
import com.lcba.model.Food;
import com.lcba.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {

    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void deleteFood(Long id) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarian,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory);

    public List<Food> searchFood(String searchTerm);

    public Food findFoodById(Long id) throws Exception;

    public Food updateAvailibilityStatus(Long id) throws Exception;

}
