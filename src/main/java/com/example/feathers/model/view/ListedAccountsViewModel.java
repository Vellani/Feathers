package com.example.feathers.model.view;

import com.example.feathers.model.entity.enums.UserRolesEnum;

public class ListedAccountsViewModel {

    private Long id;
    private String username;
    private String email;
    private UserRolesEnum accountLevel;

    public ListedAccountsViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRolesEnum getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(UserRolesEnum accountLevel) {
        this.accountLevel = accountLevel;
    }
}
