package com.lcba.service;


import com.lcba.Request.CreateFoodRequest;
import com.lcba.model.Category;
import com.lcba.model.Food;
import com.lcba.model.Restaurant;
import com.lcba.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food=new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setIngredientsItems(request.getIngredients());
        food.setSeasonal(request.isSeasonal());
        food.isVegetarian(request.isVegetarian());

        Food savedFood=foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return  savedFood;
    }

    @Override
    public void deleteFood(Long id) throws Exception {
        Food food=findFoodById(id);
        foodRepository.delete(food);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegitarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {

       List<Food> foods=foodRepository.findByRestaurantId(restaurantId);

       if (isVegitarian){
           foods=filterByVegetarian(foods,isVegitarian);
       }
       if (isNonVeg){
           foods=filterByNoVeg( foods,  isNonVeg);
       }
       if (isSeasonal){
           foods=filterBySeasonal(foods,isSeasonal);
       }
       if (foodCategory!=null && !foodCategory.equals("")){
           foods=filterByCategory(foods,foodCategory);
       }


        return List.of();
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {

        return foods.stream().filter(food -> {
            if (food.getFoodCategory()!=null){

                return food.getFoodCategory().getName().equals(foodCategory);
            }else {

                return false;
            }
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());


    }

    private List<Food> filterByNoVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());

    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarian) {

        return foods.stream().filter(food -> food.isVegetarian()==isVegitarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long id) throws Exception {

        Optional<Food> optionalFood=foodRepository.findById(id);

        if (optionalFood.isEmpty()){
            throw new Exception("Food Doesn't Exist");
        }

        return optionalFood.get();
    }

    @Override
    public Food updateAvailibilityStatus(Long id) throws Exception {

        Food food=findFoodById(id);
        food.setAvailable(!food.isAvailable());
       Food availbleFood= foodRepository.save(food);

        return availbleFood;
    }
}
