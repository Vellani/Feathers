package com.example.feathers.database.model.seed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AerodromeSeed {

    @Expose
    private String name;
    @Expose
    @SerializedName("ident")
    private String icaoCode;
    @Expose
    @SerializedName("latitude_deg")
    private String latitude;
    @Expose
    @SerializedName("longitude_deg")
    private String longitude;
    @Expose
    @SerializedName("iso_country")
    private String country;

    public AerodromeSeed() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AerodromeSeed that = (AerodromeSeed) o;
        return Objects.equals(icaoCode, that.icaoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icaoCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
