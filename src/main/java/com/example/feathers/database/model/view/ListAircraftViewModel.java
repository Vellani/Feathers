package com.example.feathers.database.model.view;

public class ListAircraftViewModel {

    private Long id;
    private String registration;
    private String icaoModelName;

    private Integer flights;

    public ListAircraftViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getIcaoModelName() {
        return icaoModelName;
    }

    public void setIcaoModelName(String icaoModelName) {
        this.icaoModelName = icaoModelName;
    }

    public Integer getFlights() {
        return flights;
    }

    public void setFlights(Integer flights) {
        this.flights = flights;
    }
}
