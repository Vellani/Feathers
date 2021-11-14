package com.example.feathers.model.entity;

import com.example.feathers.model.entity.enums.AircraftClassEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "aircraft")
public class AircraftEntity extends BaseEntity {

    private String registration;
    private String icaoModelName;
    private AircraftClassEnum aircraftClass;

    private Integer numberOfEngines;
    private String owner;

    public AircraftEntity() {
    }

    @Column(unique = true, nullable = false)
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Column(nullable = false)
    public String getIcaoModelName() {
        return icaoModelName;
    }

    public void setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
    }

    @Column(nullable = false)
    public AircraftClassEnum getAircraftClass() {
        return aircraftClass;
    }

    public void setAircraftClass(AircraftClassEnum aircraftClass) {
        this.aircraftClass = aircraftClass;
    }

    @Column(nullable = false)
    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public void setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    @Column
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return registration;
    }
}
