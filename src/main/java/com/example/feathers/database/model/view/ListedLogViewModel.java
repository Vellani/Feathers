package com.example.feathers.database.model.view;

import java.time.LocalDate;

public class ListedLogViewModel {

    private Long id;

    private LocalDate dateOfLog;
    private String departureTime;
    private String arrivalTime;
    private String departureAerodrome;
    private String arrivalAerodrome;
    private String departureFlag;
    private String arrivalFlag;
    private boolean hasGPX;

    private String registration;
    private String aircraftModel;

    public ListedLogViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ListedLogViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public ListedLogViewModel setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
        return this;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public ListedLogViewModel setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public ListedLogViewModel setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public String getDepartureAerodrome() {
        return departureAerodrome;
    }

    public ListedLogViewModel setDepartureAerodrome(String departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
        return this;
    }

    public String getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public ListedLogViewModel setArrivalAerodrome(String arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
        return this;
    }

    public String getDepartureFlag() {
        return departureFlag;
    }

    public ListedLogViewModel setDepartureFlag(String departureFlag) {
        this.departureFlag = departureFlag;
        return this;
    }

    public String getArrivalFlag() {
        return arrivalFlag;
    }

    public ListedLogViewModel setArrivalFlag(String arrivalFlag) {
        this.arrivalFlag = arrivalFlag;
        return this;
    }

    public String getRegistration() {
        return registration;
    }

    public ListedLogViewModel setRegistration(String registration) {
        this.registration = registration;
        return this;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public ListedLogViewModel setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public boolean isHasGPX() {
        return hasGPX;
    }

    public ListedLogViewModel setHasGPX(boolean hasGPX) {
        this.hasGPX = hasGPX;
        return this;
    }
}
