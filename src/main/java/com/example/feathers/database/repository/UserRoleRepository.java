package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.model.entity.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(UserRolesEnum role);

}
