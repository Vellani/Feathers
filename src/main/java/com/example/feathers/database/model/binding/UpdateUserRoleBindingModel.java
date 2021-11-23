package com.example.feathers.database.model.binding;

import com.example.feathers.util.UserRoleUtil;
import com.google.gson.annotations.Expose;


public class UpdateUserRoleBindingModel {

    @Expose
    private String id;

    @Expose
    private String roles;

    public UpdateUserRoleBindingModel() {
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getRoles() {
        return  roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
