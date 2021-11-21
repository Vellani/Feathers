package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("select u.username from UserEntity u where u.username like %:username%")
    List<String> findUsersForAdmin(String username);

    @Query("select u from UserEntity u where u.username like %:username%")
    List<UserEntity> findUsersMatchingUsername(String username);

    @Modifying
    @Query("Update UserEntity u set u.firstName = :firstName, u.lastName = :lastName, " +
            "u.address = :address, u.licenseNumber = :licenseNumber, u.email = :email where u.id = :id")
    void updateUserDetails(String firstName, String lastName, String address, String licenseNumber, String email, Long id);


    @Modifying
    @Query("Update UserEntity u set u.password = :encryptedPassword where u.id = :id")
    void updateUserPassword(String encryptedPassword, Long id);



}
