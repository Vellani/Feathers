package com.example.feathers.database.model.binding;

import javax.validation.constraints.*;

public class UserRegisterBindingModel {

    // Essential - Account
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters.")
    @NotNull
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email(message = "Enter valid email.")
    @NotNull
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotNull
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }


    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotNull
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
