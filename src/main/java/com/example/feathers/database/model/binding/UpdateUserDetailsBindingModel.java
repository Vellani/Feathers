package com.example.feathers.database.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdateUserDetailsBindingModel {

    private String username;
    private String email;
    private String roles;
    private String licenseNumber;
    private String firstName;
    private String lastName;
    private String address;

    public UpdateUserDetailsBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Email(message = "Enter valid email.")
    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Pattern(regexp = "^(?:[A-z1-9]{5,20}|)$", message = "License must be between 5 and 20 characters.")
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }


    @Pattern(regexp = "^(?:[A-z]{2,20}|)$", message = "First name must be between 5 and 20 characters.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Pattern(regexp = "^(?:[A-z]{2,20}|)$", message = "Last name must be between 5 and 20 characters.")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoles() {
        return roles;
    }

    public UpdateUserDetailsBindingModel setRoles(String roles) {
        this.roles = roles;
        return this;
    }


}
