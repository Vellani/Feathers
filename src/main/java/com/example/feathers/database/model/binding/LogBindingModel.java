package com.example.feathers.database.model.binding;

import com.example.feathers.database.model.common.CommonLogInterface;
import com.example.feathers.database.model.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;

public class LogBindingModel implements CommonLogInterface {

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

    private String remarks;
    private MultipartFile gpxLog;
    private boolean hasGPX;

    public LogBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public LogBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    @PastOrPresent(message = "The date cannot be in the future.")
    @NotNull(message = "Missing date.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public LogBindingModel setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
        return this;
    }

    @NotNull(message = "Missing departure time.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LogBindingModel setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    @NotNull(message = "Missing arrival time.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LogBindingModel setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    @NotNull(message = "Missing departure aerodrome.")
    public String getDepartureAerodrome() {
        return departureAerodrome;
    }

    public LogBindingModel setDepartureAerodrome(String departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
        return this;
    }

    @NotNull(message = "Missing arrival aerodrome.")
    public String getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public LogBindingModel setArrivalAerodrome(String arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
        return this;
    }

    @NotNull(message = "Missing registration.")
    public String getAircraft() {
        return aircraft;
    }

    public LogBindingModel setAircraft(String aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    @NotNull(message = "Set the number of landings.")
    @Positive(message = "The landings cannot be negative or 0.")
    public Integer getLandings() {
        return landings;
    }

    public LogBindingModel setLandings(Integer landings) {
        this.landings = landings;
        return this;
    }

    @NotNull(message = "Missing Pilot in command.")
    public String getPilotInCommandName() {
        return pilotInCommandName;
    }

    public LogBindingModel setPilotInCommandName(String pilotInCommandName) {
        this.pilotInCommandName = pilotInCommandName;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public LogBindingModel setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public LogBindingModel setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    public MultipartFile getGpxLog() {
        return gpxLog;
    }

    public LogBindingModel setGpxLog(MultipartFile gpxLog) {
        this.gpxLog = gpxLog;
        return this;
    }

    public boolean isHasGPX() {
        return hasGPX;
    }

    public LogBindingModel setHasGPX(boolean hasGPX) {
        this.hasGPX = hasGPX;
        return this;
    }
}
