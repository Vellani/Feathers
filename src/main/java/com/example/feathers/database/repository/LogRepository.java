package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.LogEntity;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    @Query("select l from LogEntity l where l.creator.username like :username order by l.dateOfLog desc , l.departureTime desc")
    List<LogEntity> findAllAndOrderByDateThenTime(String username);

    void deleteLogEntitiesByCreator(UserEntity creator);

    @Query("select count(l) from LogEntity l where l.aircraft = :aircraft")
    Integer countByCreator_Aircraft(AircraftEntity aircraft);

    Optional<LogEntity> findByIdAndCreator_Username(Long id, String creator);
}
