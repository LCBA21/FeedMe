package com.lcba.controller;


import com.lcba.model.Category;
import com.lcba.model.User;
import com.lcba.service.CategoryService;
import com.lcba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


@PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        Category createCategory=categoryService.createCategory(category.getName(),user.getId());

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @GetMapping("/getRestaurantCategory")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserByJwtToken(jwt);
        List<Category> createCategory=categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

}
