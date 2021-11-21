package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {


    Optional<ReviewEntity> findByCreator(UserEntity creator);
}
