package com.lcba.service;

import com.lcba.model.Category;
import com.lcba.model.Restaurant;
import com.lcba.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public Category createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);

        Category category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(id);

        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> retrievedCategory=categoryRepository.findById(id);
        if (retrievedCategory.isEmpty()){
            throw new Exception("Category Not Found");
        }


        return retrievedCategory.get();
    }
}
