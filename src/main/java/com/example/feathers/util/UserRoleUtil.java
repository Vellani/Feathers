package com.example.feathers.util;

import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserRoleUtil {

    private final UserRoleEntity userRole;
    private final UserRoleEntity suspendedRole;
    private final UserRoleEntity vipRole;
    private final UserRoleEntity adminRole;

    public UserRoleUtil(UserRoleRepository userRoleRepository) {
        this.userRole = new UserRoleEntity().setRole(UserRolesEnum.USER);
        this.suspendedRole = new UserRoleEntity().setRole(UserRolesEnum.SUSPENDED);
        this.vipRole = new UserRoleEntity().setRole(UserRolesEnum.VIP);
        this.adminRole = new UserRoleEntity().setRole(UserRolesEnum.ADMIN);

        if (userRoleRepository.count() == 0) {
            userRoleRepository.save(this.userRole);
            userRoleRepository.save(this.adminRole);
            userRoleRepository.save(this.vipRole);
            userRoleRepository.save(this.suspendedRole);
        }
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
}
