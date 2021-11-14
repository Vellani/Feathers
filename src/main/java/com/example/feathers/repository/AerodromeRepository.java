package com.example.feathers.repository;

import com.example.feathers.model.entity.AerodromeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AerodromeRepository extends JpaRepository<AerodromeEntity, Long> {

    @Query("select a.name from AerodromeEntity a where a.name like %:aero%")
    List<String> findAllMatchingAerodromes(String aero);

    Optional<AerodromeEntity> findByName(String name);

    boolean existsByName(String name);
}
