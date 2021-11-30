package com.example.feathers.database.model.service;

import com.example.feathers.database.model.entity.AerodromeEntity;
import com.example.feathers.database.model.entity.AircraftEntity;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;

public class LogServiceModel {

    private Long id;
    private LocalDate dateOfLog;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private AerodromeEntity departureAerodrome;
    private AerodromeEntity arrivalAerodrome;
    private AircraftEntity aircraft;
    private Integer landings;
    private String pilotInCommandName;
    private UserEntity creator;
    private Byte[] gpxLog;

    private String remarks;

    public LogServiceModel() {
    }

    public UserEntity getCreator() {
        return creator;
    }

    public LogServiceModel setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public AerodromeEntity getDepartureAerodrome() {
        return departureAerodrome;
    }

    public LogServiceModel setDepartureAerodrome(AerodromeEntity departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
        return this;
    }

    public AerodromeEntity getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public LogServiceModel setArrivalAerodrome(AerodromeEntity arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
        return this;
    }

    public Integer getLandings() {
        return landings;
    }

    public void setLandings(Integer landings) {
        this.landings = landings;
    }

    public String getPilotInCommandName() {
        return pilotInCommandName;
    }

    public void setPilotInCommandName(String pilotInCommandName) {
        this.pilotInCommandName = pilotInCommandName;
    }

    public AircraftEntity getAircraft() {
        return aircraft;
    }

    public LogServiceModel setAircraft(AircraftEntity aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Byte[] getGpxLog() {
        return gpxLog;
    }

    public void setGpxLog(Byte[] gpxLog) {
        this.gpxLog = gpxLog;
    }


}
