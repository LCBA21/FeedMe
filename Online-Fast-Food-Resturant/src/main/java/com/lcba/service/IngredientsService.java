package com.lcba.service;


import com.lcba.model.IngredientsCategory;
import com.lcba.model.IngredientsItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IngredientsService {

    public IngredientsCategory createIngredientCategory(String name,Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientCategoryByRestaurantId( Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId)throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id)throws Exception;





}
