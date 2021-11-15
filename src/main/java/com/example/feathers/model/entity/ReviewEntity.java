package com.example.feathers.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    // TODO make at creation of the 3rd Log -> Ask user for review
    private String reviewBody;
    private Integer stars;
    private UserEntity creator;

    public ReviewEntity() {
    }

    @Column(columnDefinition = "Text")
    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    @Column
    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    @ManyToOne
    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }
}
