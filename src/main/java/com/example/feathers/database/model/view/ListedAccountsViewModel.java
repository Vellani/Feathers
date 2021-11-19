package com.example.feathers.database.model.view;

import com.example.feathers.database.model.entity.UserRoleEntity;

public class ListedAccountsViewModel {

    private Long id;
    private String username;
    private String email;
    private UserRoleEntity accountLevel;

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

    public UserRoleEntity getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(UserRoleEntity accountLevel) {
        this.accountLevel = accountLevel;
    }
}
