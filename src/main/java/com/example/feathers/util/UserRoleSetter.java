package com.example.feathers.util;

import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import com.example.feathers.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserRoleSetter {

    private final UserRoleRepository userRoleRepository;

    public UserRoleSetter(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Set<UserRoleEntity> setUserRole() {
        return Set.of(user());
    }
    public Set<UserRoleEntity> setVIPRole() {
        return Set.of(user(),vip());
    }
    public Set<UserRoleEntity> setAdminRole() {
        return Set.of(user(), vip(), admin());
    }
    public Set<UserRoleEntity> setSuspendedRole() {
        return Set.of(suspended());
    }

    private UserRoleEntity user() {
        return userRoleRepository.findByRole(UserRolesEnum.USER);
    }
    private UserRoleEntity suspended() {
        return userRoleRepository.findByRole(UserRolesEnum.SUSPENDED);
    }
    private UserRoleEntity vip() {
        return userRoleRepository.findByRole(UserRolesEnum.VIP);
    }
    private UserRoleEntity admin() {
        return userRoleRepository.findByRole(UserRolesEnum.ADMIN);
    }
}
