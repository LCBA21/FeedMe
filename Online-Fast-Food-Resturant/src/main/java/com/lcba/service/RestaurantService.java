package com.lcba.service;

import com.lcba.Request.CreateRestuarantRequest;
import com.lcba.dto.RestrurantDto;
import com.lcba.model.Restaurant;
import com.lcba.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestuarantRequest request, User user);

    public Restaurant updateRestaurant(Long id,CreateRestuarantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long id) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long id) throws Exception;

    public RestrurantDto addToFavorites(Long id,User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;


}
