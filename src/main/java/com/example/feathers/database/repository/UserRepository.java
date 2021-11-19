package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByUsername(String username);


    @Query("select u.username from UserEntity u where u.username like %:username%")
    List<String> findUserForAdmin(String username);

    @Query("select u from UserEntity u where u.username like %:username%")
    List<UserEntity> findUsersMathingUsername(String username);
}
