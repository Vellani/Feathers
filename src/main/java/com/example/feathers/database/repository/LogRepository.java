package com.example.feathers.database.repository;

import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.LogEntity;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    @Query("Select l.gpxLog from LogEntity l where l.id = :id")
    Byte[] findSpecificGPXLogById(Long id);

    @Query("Select l.gpxLog from LogEntity l where l.creator.username like :username")
    List<Byte[]> findAllGpxFilesForUsername(String username);

    @Transactional
    @Query("Delete from LogEntity l where l.creator is null or l.aircraft is null")
    void cleanUpDatabase();




}
