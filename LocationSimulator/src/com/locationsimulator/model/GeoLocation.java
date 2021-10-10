package com.locationsimulator.model;

public class GeoLocation {

    String latitude;
    String longitude;

    //input string having comma separated latitude and longitude
    public GeoLocation(String rawString) {
        if (rawString == null || rawString.trim().length() == 0
                || !rawString.contains(",") || rawString.split(",").length > 2) {
            throw new IllegalStateException("Input map co-ordinates are not valid");
        }
        String[] tokens = rawString.split(",");
        String latitude = tokens[0];
        String longitude = tokens[1];
        if (Double.parseDouble(latitude) < -90 && Double.parseDouble(latitude) > 90) {
            throw new IllegalStateException("Input latitude is not valid. Given : " + latitude + " \n " + "Latitude should be between -90 to 90");
        }
        if (Double.parseDouble(longitude) < -180 && Double.parseDouble(longitude) > 180) {
            throw new IllegalStateException("Input longitude is not valid. Given : " + longitude + " \n " + "longitude should be between -180 to 180");
        }
        setLatitude(tokens[0]);
        setLongitude(tokens[1]);
    }

    public GeoLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
