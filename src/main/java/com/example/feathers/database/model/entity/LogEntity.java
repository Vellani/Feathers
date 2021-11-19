package com.example.feathers.database.model.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity {

    //private UserEntity createdBy;
    private UserEntity creator;
    private LocalDate dateOfLog;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private AerodromeEntity departureAerodrome;
    private AerodromeEntity arrivalAerodrome;
    private Integer landings;

    private String pilotInCommandName;
    private AircraftEntity aircraft;
    private String remarks;
    private Byte[] gpxLog;

    public LogEntity() {
    }

    @ManyToOne
    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    @Column(nullable = false)
    public LocalDate getDateOfLog() {
        return dateOfLog;
    }

    public void setDateOfLog(LocalDate dateOfLog) {
        this.dateOfLog = dateOfLog;
    }

    @Column(nullable = false)
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    @Column(nullable = false)
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @ManyToOne
    public AerodromeEntity getDepartureAerodrome() {
        return departureAerodrome;
    }

    public void setDepartureAerodrome(AerodromeEntity departureAerodrome) {
        this.departureAerodrome = departureAerodrome;
    }

    @ManyToOne
    public AerodromeEntity getArrivalAerodrome() {
        return arrivalAerodrome;
    }

    public void setArrivalAerodrome(AerodromeEntity arrivalAerodrome) {
        this.arrivalAerodrome = arrivalAerodrome;
    }

    @Column(nullable = false)
    public Integer getLandings() {
        return landings;
    }

    public void setLandings(Integer landings) {
        this.landings = landings;
    }

    @Column(nullable = false)
    public String getPilotInCommandName() {
        return pilotInCommandName;
    }

    public void setPilotInCommandName(String pilotInCommandName) {
        this.pilotInCommandName = pilotInCommandName;
    }

    @ManyToOne
    public AircraftEntity getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftEntity aircraft) {
        this.aircraft = aircraft;
    }

    @Column
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Lob
    public Byte[] getGpxLog() {
        return gpxLog;
    }

    public void setGpxLog(Byte[] gpxLog) {
        this.gpxLog = gpxLog;
    }
}
