package com.example.feathers.database.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    // Essential - Account
    private String username;
    private String email;
    private String password;
    private Set<UserRoleEntity> roles;
    private Set<LogEntity> logs;
    private Set<AircraftEntity> aircraft;
    private ReviewEntity review;

    // Essential - Profile
    private String licenseNumber;

    // Non Essential - Personal
    private String firstName;
    private String lastName;
    private String address;


    public UserEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    public Set<LogEntity> getLogs() {
        return logs;
    }

    public void setLogs(Set<LogEntity> logs) {
        this.logs = logs;
    }

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AircraftEntity> getAircraft() {
        return aircraft;
    }

    public void setAircraft(Set<AircraftEntity> aircraft) {
        this.aircraft = aircraft;
    }

    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    public ReviewEntity getReview() {
        return review;
    }

    public void setReview(ReviewEntity review) {
        this.review = review;
    }


    @Column
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public UserEntity setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        return this;
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getAddress() {
        return address;
    }

    public UserEntity setAddress(String address) {
        this.address = address;
        return this;
    }




}
