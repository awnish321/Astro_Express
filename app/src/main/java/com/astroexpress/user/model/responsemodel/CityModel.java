package com.astroexpress.user.model.responsemodel;

public class CityModel {
    String name;
    String state;
    String lat;
    String lon;

    public CityModel() {
    }

    public CityModel(String name, String state, String lat, String lon) {
        this.name = name;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
