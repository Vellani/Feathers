package com.example.feathers.database.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotNull
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
