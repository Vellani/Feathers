package com.example.feathers.database.model.binding;


import com.example.feathers.database.model.entity.enums.AircraftClassEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AircraftBindingModel {

    private Long id;
    private String registration;
    private String icaoModelName;
    private MultipartFile pictureFile;
    private String pictureUrl;
    private AircraftClassEnum aircraftClass;

    private Integer numberOfEngines;
    private String owner;

    public AircraftBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public AircraftBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    @Size(min = 4, max = 8, message = "The aircraft registration must be between 4 and 8 characters.")
    @NotBlank
    public String getRegistration() {
        return registration;
    }

    public AircraftBindingModel setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    @Size(min = 4, max = 8, message = "The aircraft model must be between 4 and 8 characters.")
    @NotBlank
    public String getIcaoModelName() {
        return icaoModelName;
    }

    public AircraftBindingModel setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
        return this;
    }

    @NotNull
    public AircraftClassEnum getAircraftClass() {
        return aircraftClass;
    }

    public AircraftBindingModel setAircraftClass(AircraftClassEnum aircraftClass) {
        this.aircraftClass = aircraftClass;
        return this;
    }

    @NotBlank(message = "Please input the number of engines.")
    @Positive(message = "The engines must be a positive number.")
    public Integer getNumberOfEngines() {
        return numberOfEngines;
    }

    public AircraftBindingModel setNumberOfEngines(Integer numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
        return this;
    }

    @Size(max = 500, message = "You have exceeded the maximum of 500 allowed characters.")
    public String getOwner() {
        return owner;
    }

    public AircraftBindingModel setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }
    public AircraftBindingModel setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public AircraftBindingModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
