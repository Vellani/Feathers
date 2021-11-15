package com.example.feathers.repository;

import com.example.feathers.model.entity.UserRoleEntity;
import com.example.feathers.model.entity.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(UserRolesEnum role);
}
