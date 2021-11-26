package com.example.feathers.database.model.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

    private UserEntity creator;
    private String content;
    private Integer rating;

    public ReviewEntity() {
    }

    @OneToOne
    public UserEntity getCreator() {
        return creator;
    }

    public ReviewEntity setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
