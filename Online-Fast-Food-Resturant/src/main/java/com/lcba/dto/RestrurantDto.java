package com.lcba.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Embeddable
public class RestrurantDto {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;

    private Long id;

    // Explicit getter and setter for `title`
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Explicit getter and setter for `images`
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    // Explicit getter and setter for `description`
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Explicit getter and setter for `id`
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
