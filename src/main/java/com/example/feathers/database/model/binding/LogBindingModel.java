package com.example.feathers.database.model.binding;

import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

public class LogBindingModel {

    private Long id;
    private LocalDate dateOfLog;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String departureAerodrome;
    private String arrivalAerodrome;
    private String aircraft;
    private Integer landings;
    private String pilotInCommandName;
    private UserEntity creator;
    //private Integer aircraftID;

    private String remarks;
    //private String gpx;

    public LogBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PastOrPresent(message = "The date cannot be in the future.")
    @NotNull(message = "Missing date.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
    }

    @NotNull(message = "Missing departure time.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    @NotNull(message = "Missing arrival time.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @NotNull(message = "Missing departure aerodrome.")
    public String getDepartureAerodrome() {
        return departureAerodrome;
    }

    public void setDepartureAerodrome(String departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
    }

    @NotNull(message = "Missing arrival aerodrome.")
    public String getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public void setArrivalAerodrome(String arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
    }

    @NotNull(message = "Missing registration.")
    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    @NotNull(message = "Set the number of landings.")
    @Positive(message = "The landings cannot be negative or 0.")
    public Integer getLandings() {
        return landings;
    }

    public void setLandings(Integer landings) {
        this.landings = landings;
    }

    @NotNull(message = "Missing Pilot in command.")
    public String getPilotInCommandName() {
        return pilotInCommandName;
    }

    public void setPilotInCommandName(String pilotInCommandName) {
        this.pilotInCommandName = pilotInCommandName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }
}
