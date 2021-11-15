package com.example.feathers.repository;

import com.example.feathers.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    @Query("select l from LogEntity l order by l.dateOfLog desc , l.departureTime desc")
    List<LogEntity> findAllAndOrderByDateThenTime();
}
