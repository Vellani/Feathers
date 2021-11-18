package com.example.feathers.model.binding;


import com.example.feathers.model.entity.enums.AircraftClassEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AircraftBindingModel {

    private Long id;
    private String registration;
    private String icaoModelName;
    private AircraftClassEnum aircraftClass;

    private Integer numberOfEngines;
    private String owner;

    public AircraftBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(min = 4, max = 8, message = "The aircraft registration must be between 4 and 8 characters.")
    @NotNull
    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Size(min = 4, max = 8, message = "The aircraft model must be between 4 and 8 characters.")
    @NotNull
    public String getIcaoModelName() {
        return icaoModelName;
    }

    public void setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
    }

    @NotNull
    public AircraftClassEnum getAircraftClass() {
        return aircraftClass;
    }

    public void setAircraftClass(AircraftClassEnum aircraftClass) {
        this.aircraftClass = aircraftClass;
    }

    @NotNull(message = "Please input the number of engines.")
    @Positive(message = "The engines must be a positive number.")
    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public void setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    @Size(max = 500, message = "You have exceeded the maximum of 500 allowed characters.")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
