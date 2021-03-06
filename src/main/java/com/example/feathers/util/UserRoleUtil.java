package com.example.feathers.util;

import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import com.example.feathers.database.repository.UserRoleRepository;

import java.util.Set;

public class UserRoleUtil {
    // Boilerplate Code to make user roles more easily managed

    private final UserRoleEntity userRole;
    private final UserRoleEntity suspendedRole;
    private final UserRoleEntity vipRole;
    private final UserRoleEntity adminRole;

    public UserRoleUtil(UserRoleRepository userRoleRepository) {
        if (userRoleRepository.count() == 0) {
            userRoleRepository.save(new UserRoleEntity().setRole(UserRolesEnum.USER));
            userRoleRepository.save(new UserRoleEntity().setRole(UserRolesEnum.ADMIN));
            userRoleRepository.save(new UserRoleEntity().setRole(UserRolesEnum.VIP));
            userRoleRepository.save(new UserRoleEntity().setRole(UserRolesEnum.SUSPENDED));
        }
        this.userRole = userRoleRepository.findByRole(UserRolesEnum.USER);
        this.suspendedRole = userRoleRepository.findByRole(UserRolesEnum.SUSPENDED);
        this.vipRole = userRoleRepository.findByRole(UserRolesEnum.VIP);
        this.adminRole = userRoleRepository.findByRole(UserRolesEnum.ADMIN);
    }

    public Set<UserRoleEntity> setUserRole() {
        return Set.of(this.userRole);
    }
    public Set<UserRoleEntity> setVipRole() {
        return Set.of(this.userRole, this.vipRole);
    }
    public Set<UserRoleEntity> setAdminRole() {
        return Set.of(this.userRole, this.vipRole, this.adminRole);
    }
    public Set<UserRoleEntity> setSuspendedRole() {
        return Set.of(this.suspendedRole);
    }

    public UserRoleEntity getUserRole() {
        return this.userRole;
    }
    public UserRoleEntity getVipRole() {
        return this.vipRole;
    }
    public UserRoleEntity getAdminRole() {
        return this.adminRole;
    }
    public UserRoleEntity getSuspendedRole() {
        return this.suspendedRole;
    }

    public UserRoleEntity findHighestRole(Set<UserRoleEntity> roles) {
        if (roles.contains(getAdminRole())) {
            return getAdminRole();
        } else if (roles.contains(getVipRole())) {
            return getVipRole();
        } else if (roles.contains(getUserRole())) {
            return getUserRole();
        } else {
            return getSuspendedRole();
        }
    }

    public Set<UserRoleEntity> setRoleFromString(String role) {
        return switch (role) {
            case "SUSPENDED" -> this.setSuspendedRole();
            case "USER" -> this.setUserRole();
            case "VIP" -> this.setVipRole();
            case "ADMIN" -> this.setAdminRole();
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
    }

}
