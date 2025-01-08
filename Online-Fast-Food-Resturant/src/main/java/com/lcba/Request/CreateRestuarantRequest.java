package com.lcba.Request;


import com.lcba.model.Address;
import com.lcba.model.ContactInformation;
import lombok.Data;

import java.util.List;

public class CreateRestuarantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

    // Explicit getter for `id`
    public Long getId() {
        return id;
    }

    // Explicit setter for `id`
    public void setId(Long id) {
        this.id = id;
    }

    // Explicit getter for `name`
    public String getName() {
        return name;
    }

    // Explicit setter for `name`
    public void setName(String name) {
        this.name = name;
    }

    // Explicit getter for `description`
    public String getDescription() {
        return description;
    }

    // Explicit setter for `description`
    public void setDescription(String description) {
        this.description = description;
    }

    // Explicit getter for `cuisineType`
    public String getCuisineType() {
        return cuisineType;
    }

    // Explicit setter for `cuisineType`
    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    // Explicit getter for `address`
    public Address getAddress() {
        return address;
    }

    // Explicit setter for `address`
    public void setAddress(Address address) {
        this.address = address;
    }

    // Explicit getter for `contactInformation`
    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    // Explicit setter for `contactInformation`
    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    // Explicit getter for `openingHours`
    public String getOpeningHours() {
        return openingHours;
    }

    // Explicit setter for `openingHours`
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    // Explicit getter for `images`
    public List<String> getImages() {
        return images;
    }

    // Explicit setter for `images`
    public void setImages(List<String> images) {
        this.images = images;
    }
}
