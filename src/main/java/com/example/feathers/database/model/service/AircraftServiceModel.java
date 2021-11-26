package com.example.feathers.database.model.service;

import com.example.feathers.database.model.entity.enums.AircraftClassEnum;

public class AircraftServiceModel {

    private Long id;
    private String registration;
    private String icaoModelName;
    private AircraftClassEnum aircraftClass;
    private String pictureUrl;
    private String picturePublicId;

    private Integer numberOfEngines;

    private String owner;

    public AircraftServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration.toUpperCase();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public AircraftServiceModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getPicturePublicId() {
        return picturePublicId;
    }

    public AircraftServiceModel setPicturePublicId(String picturePublicId) {
        this.picturePublicId = picturePublicId;
        return this;
    }

    public String getIcaoModelName() {
        return icaoModelName;
    }

    public void setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName.toUpperCase();
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
