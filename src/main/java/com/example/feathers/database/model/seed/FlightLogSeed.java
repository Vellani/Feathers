package com.example.feathers.database.model.seed;

import com.example.feathers.database.model.common.CommonLogInterface;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightLogSeed implements CommonLogInterface {


    @Expose
    @SerializedName("date-of-departure")
    private LocalDate dateOfLog;

    @Expose
    @SerializedName("dep-time")
    private LocalTime departureTime;

    @Expose
    @SerializedName("arr-time")
    private LocalTime arrivalTime;

    @Expose
    @SerializedName("dep-aero")
    private String departureAerodrome;

    @Expose
    @SerializedName("arr-aero")
    private String arrivalAerodrome;

    @Expose
    @SerializedName("registration")
    private String aircraft;

    @Expose
    private Integer landings;

    @Expose
    @SerializedName("pic")
    private String pilotInCommandName;

    @Expose
    @SerializedName("owner")
    private String creator;

    @Expose
    @SerializedName("gpx-file")
    private String gpxLog;

    public FlightLogSeed() {
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

    public String getDepartureAerodrome() {
        return departureAerodrome;
    }

    public void setDepartureAerodrome(String departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
    }

    public String getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public void setArrivalAerodrome(String arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getGpxLog() {
        return gpxLog;
    }

    public void setGpxLog(String gpxLog) {
        this.gpxLog = gpxLog;
    }
}
