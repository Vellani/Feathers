package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {

    Optional<AircraftEntity> findByRegistration(String registration);

    @Query("select a.registration from AircraftEntity a where a.creator.username like :username and a.registration like %:reg% order by a.registration")
    List<String> findAllMatchingRegistrations(String username, String reg);

    boolean existsByCreator_UsernameAndRegistration(String creator_username, String registration);

    List<AircraftEntity> findByCreator_Username(String creator_username);

    Optional<AircraftEntity> findByIdAndCreator_Username(Long id, String name);

    @Transactional
    @Query("Delete from AircraftEntity a where a.creator is null")
    void cleanUpDatabase();
}
