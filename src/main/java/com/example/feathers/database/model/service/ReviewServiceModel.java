package com.example.feathers.database.model.service;

import com.example.feathers.database.model.entity.UserEntity;

public class ReviewServiceModel {

    private UserEntity creator;
    private String content;
    private Integer rating;

    public ReviewServiceModel() {
    }

    public UserEntity getCreator() {
        return creator;
    }

    public ReviewServiceModel setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ReviewServiceModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
