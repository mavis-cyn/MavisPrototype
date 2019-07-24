package com.example.mavis_prototype;

public class LocationDetails {
    private String markerID;
    private String busStop;
    private String category;
    private double latitude;
    private double longitude;
    private String name;
    private String openingHours;
    private String description;

    public LocationDetails(String  markerID, String busStop, String category, double latitude, double longitude, String name, String openingHours, String description) {
        this.markerID = markerID;
        this.busStop = busStop;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.openingHours = openingHours;
        this.description = description;
    }

    public String getMarkerID() {
        return markerID;
    }

    public String getBusStop() {
        return busStop;
    }

    public String getCategory() {
        return category;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getDescription() { return description; }
}
