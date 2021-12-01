package com.example.feathers.database.model.entity;

import com.example.feathers.database.model.entity.enums.AircraftClassEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "aircraft")
public class AircraftEntity extends BaseEntity {

    private String registration;
    private String icaoModelName;
    private String pictureUrl;
    private String picturePublicId;
    private AircraftClassEnum aircraftClass;
    private Set<LogEntity> logs;

    private Integer numberOfEngines;
    private UserEntity creator;

    public AircraftEntity() {
    }


    @Column(unique = true, nullable = false)
    public String getRegistration() {
        return registration;
    }

    public AircraftEntity setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    @Column(nullable = false)
    public String getIcaoModelName() {
        return icaoModelName;
    }

    public AircraftEntity setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
        return this;
    }

    @Column
    public String getPictureUrl() {
        return pictureUrl;
    }

    public AircraftEntity setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    @Column
    public String getPicturePublicId() {
        return picturePublicId;
    }

    public AircraftEntity setPicturePublicId(String picturePublicId) {
        this.picturePublicId = picturePublicId;
        return this;
    }

    @Column(nullable = false)
    public AircraftClassEnum getAircraftClass() {
        return aircraftClass;
    }

    public AircraftEntity setAircraftClass(AircraftClassEnum aircraftClass) {
        this.aircraftClass = aircraftClass;
        return this;
    }

    @Column(nullable = false)
    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public AircraftEntity setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public UserEntity getCreator() {
        return creator;
    }

    public AircraftEntity setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    @OneToMany(mappedBy = "aircraft", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<LogEntity> getLogs() {
        return logs;
    }

    public void setLogs(Set<LogEntity> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return this.getRegistration();
    }
}
