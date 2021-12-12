package com.example.feathers.database.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserPasswordBindingModel {

    private String password;
    private String confirmPassword;

    public UpdateUserPasswordBindingModel() {
    }

    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotBlank
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters.")
    @NotBlank
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
