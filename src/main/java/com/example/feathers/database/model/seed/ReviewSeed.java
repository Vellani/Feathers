package com.example.feathers.database.model.seed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewSeed {

    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("creator")
    private String creator;
    @Expose
    @SerializedName("rating")
    private Integer rating;


    public ReviewSeed() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
