package com.example.feathers.repository;

import com.example.feathers.model.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {

    boolean existsByRegistration(String registration);

    Optional<AircraftEntity> findByRegistration(String registration);

    @Query("select a.registration from AircraftEntity a where a.creator.username like :username and a.registration like %:reg%")
    List<String> findAllMatchingRegistrations(String username, String reg);
}
