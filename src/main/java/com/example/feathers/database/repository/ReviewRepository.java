package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.ReviewEntity;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query(value = "select r from ReviewEntity r where r.rating > 4 and r.creator.firstName is not null")
    List<ReviewEntity> findReviewsToDisplay();

    Optional<ReviewEntity> findByCreator(UserEntity creator);

    @Transactional
    @Query("Delete from ReviewEntity r where r.creator is null")
    void cleanUpDatabase();

}
