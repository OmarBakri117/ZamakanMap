package Intities;

import java.util.ArrayList;

public class Event {

   private String eventName ;
   private int fromDate ;
   private int toDate ;
   private String bio ;
   private String description ;
   private String picture ;
   private String refLink ;
   private double Latitude ;
   private double Longitude ;
   private String countries ;

    public Event(String eventName, int fromDate, int toDate, String bio,
                 String description, String picture, String refLink, double latitude,
                 double longitude, String countries) {
        this.eventName = eventName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.bio = bio;
        this.description = description;
        this.picture = picture;
        this.refLink = refLink;
        Latitude = latitude;
        Longitude = longitude;
        this.countries = countries;
    }

    public Event() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }
}
