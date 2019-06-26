package com.example.mavis_prototype;

public class InfoWindowData {
    private String image;
    private String opening_hours;
    private String bus_stops;

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getOpeningHours() {
        return opening_hours;
    }

    public void setOpeningHours(String opening_hours){
        this.opening_hours = opening_hours;
    }

    public String getBusStops() {
        return bus_stops;
    }

    public void setBusStops(String bus_stops){
        this.bus_stops = bus_stops;
    }
}
