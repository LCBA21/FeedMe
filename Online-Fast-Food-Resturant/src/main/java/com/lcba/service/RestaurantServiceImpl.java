package com.lcba.service;

import com.lcba.Request.CreateRestuarantRequest;
import com.lcba.dto.RestrurantDto;
import com.lcba.model.Address;
import com.lcba.model.Restaurant;
import com.lcba.model.User;
import com.lcba.repository.AddressRepository;
import com.lcba.repository.RestaurantRepository;
import com.lcba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestuarantRequest request, User user) {

        // Save the address if it's not null
        Address address = null;
        if (request.getAddress() != null) {
            address = addressRepository.save(request.getAddress());
        }

        // Map request fields to the restaurant entity
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(request.getContactInformation()); // Directly set if embedded
        restaurant.setCuisineType(request.getCuisineType()); // Corrected
        restaurant.setDescription(request.getDescription());
        restaurant.setImages(request.getImages());
        restaurant.setName(request.getName());
        restaurant.setOpeningHours(request.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }


    @Override
    public Restaurant updateRestaurant(Long id, CreateRestuarantRequest updatedRestaurant) throws Exception {
       Restaurant restaurant=findRestaurantById(id);

       if (restaurant.getCuisineType()!=null){
           restaurant.setCuisineType(updatedRestaurant.getCuisineType());
       }

        if (restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if (restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }


        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {

        Restaurant restaurant=findRestaurantById(id);

        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if (opt.isEmpty()){
            throw new Exception("Restaurant not found with id"+id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
       Restaurant restaurant=restaurantRepository.findByOwnerId(id);

       if(restaurant== null){
           throw new Exception("Restaurant not found with Owner id"+id);
       }

        return restaurant;
    }

    @Override
    public RestrurantDto addToFavorites(Long id, User user) throws Exception {

        Restaurant restaurant=findRestaurantById(id);

        RestrurantDto dto=new RestrurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurant.getId());


        boolean isFavorited=false;
        List<RestrurantDto> favourites =user.getFavourites();
        for (RestrurantDto favorite: favourites){
            if (favorite.getId().equals(id)){
                isFavorited=true;
                break;
            }
        }

        if (isFavorited){
            favourites.removeIf(favorite ->favorite.getId().equals(id));
        }else {
            favourites.add(dto);
        }

        userRepository.save(user);


        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
       Restaurant restaurant=findRestaurantById(id);

       restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
