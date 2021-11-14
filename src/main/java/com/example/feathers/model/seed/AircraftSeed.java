package com.example.feathers.model.seed;

import com.example.feathers.model.entity.enums.AircraftClassEnum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AircraftSeed {

    @Expose
    private String registration;
    @Expose
    @SerializedName("icao_mode_name")
    private String icaoModelName;
    @Expose
    @SerializedName("aircraft_class")
    private String aircraftClass;
    @Expose
    @SerializedName("number_of_engines")
    private Integer numberOfEngines;
    @Expose
    private String owner;

    public AircraftSeed() {
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

    public String getAircraftClass() {
        return aircraftClass;
    }

    public void setAircraftClass(String aircraftClass) {
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
