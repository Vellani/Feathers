package com.example.feathers.model.service;

import com.example.feathers.model.entity.AerodromeEntity;
import com.example.feathers.model.entity.AircraftEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogServiceModel {

    private Long id;
    private LocalDate dateOfLog;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private AerodromeEntity departureAerodrome;
    private AerodromeEntity arrivalAerodrome;
    private Integer landings;

    private String pilotInCommandName;
    private AircraftEntity aircraft;
    private String remarks;

    public LogServiceModel() {
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
}
