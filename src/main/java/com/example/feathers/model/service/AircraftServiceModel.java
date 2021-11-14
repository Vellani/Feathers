package com.example.feathers.model.service;

import com.example.feathers.model.entity.enums.AircraftClassEnum;

public class AircraftServiceModel {

    private String registration;
    private String icaoModelName;
    private AircraftClassEnum aircraftClass;

    private Integer numberOfEngines;
    private String owner;

    public AircraftServiceModel() {
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getIcaoModelName() {
        return icaoModelName;
    }

    public void setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
    }

    public AircraftClassEnum getAircraftClass() {
        return aircraftClass;
    }

    public void setAircraftClass(AircraftClassEnum aircraftClass) {
        this.aircraftClass = aircraftClass;
    }

    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public void setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
