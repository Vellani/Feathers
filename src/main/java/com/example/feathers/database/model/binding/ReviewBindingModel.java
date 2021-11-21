package com.example.feathers.database.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewBindingModel {

    private String content;
    private Integer rating;

    public ReviewBindingModel() {
    }

    @NotNull(message = "Please leave a comment!")
    @Size(min = 10, message = "Too short")
    @Size(max = 500, message = "Too long")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
