package com.example.feathers.database.model.entity;

import com.example.feathers.database.model.entity.enums.UserRolesEnum;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
