package com.cosmin;

class Country {
    private String countryName;

    public Country() {
        this("No country");
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}

class District extends Country {
    private String districtName;

    public District() {
        this("No country", "No district");
    }

    public District(String countryName, String districtName) {
        super(countryName);
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}

public class City extends District {
    private String city;

    public City() {
        this("No country", "No district", "No city");
    }

    public City(String countryName, String districtName, String cityName) {
        super(countryName, districtName);
        this.city = cityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String cityName) {
        this.city = cityName;
    }

    @Override
    public String toString() {
        return city;
    }
}
