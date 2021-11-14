package com.example.feathers.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "aerodromes")
public class AerodromeEntity extends BaseEntity {

    //private Boolean flagForAPICall;

    private String name;
    private String icaoCode;
    private String country;

    // For GPS Heatmap
    private String latitude;
    private String longitude;

    // TODO maybe add OneToMany for dep/arr for admin panel ?

    public AerodromeEntity() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    @Column()
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}


