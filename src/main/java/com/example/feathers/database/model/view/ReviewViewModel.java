package com.example.feathers.database.model.view;

public class ReviewViewModel {

    private String firstName;
    private String lastName;
    private String content;
    private Integer rating;

    public ReviewViewModel() {
    }
    public String getFirstName() {
        return firstName;
    }

    public ReviewViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ReviewViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ReviewViewModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
