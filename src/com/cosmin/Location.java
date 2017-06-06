package com.cosmin;

import java.util.List;

public class Location {
    private String locationName;
    private City cityName;
    private double dayPrice;
    private List<String> activities;
    private List<String> dates;
    public Location(){
        this("No location",null,0,null,null);
    }

    public Location(String locationName, City cityName, double dayPrice, List<String> activities,List <String> dates) {
        this.locationName = locationName;
        this.cityName = cityName;
        this.dayPrice = dayPrice;
        this.activities = activities;
        this.dates = dates;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public City getCityName() {
        return cityName;
    }

    public void setCityName(City cityName) {
        this.cityName = cityName;
    }

    public double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(double dayPrice) {
        this.dayPrice = dayPrice;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "Nume Locatie: " + locationName + '\n' +
                " Oras: " + cityName + '\n'+
                " Pret/zi: " + dayPrice +'\n'+
                " Activitati: " + activities +'\n'+
                " Perioada: " + dates;
    }
}
