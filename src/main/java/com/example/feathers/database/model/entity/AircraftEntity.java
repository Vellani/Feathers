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

    private Integer numberOfEngines;
    private UserEntity creator;

    public AircraftEntity() {
    }

    @ManyToOne
    public UserEntity getCreator() {
        return creator;
    }

    public AircraftEntity setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
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


    @Override
    public String toString() {
        return this.getRegistration();
    }
}
