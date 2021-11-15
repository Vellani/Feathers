package com.example.feathers.model.entity;

import com.example.feathers.model.entity.enums.UserRolesEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    private UserRolesEnum role;

    @Enumerated
    public UserRolesEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRolesEnum role) {
        this.role = role;
        return this;
    }
}
