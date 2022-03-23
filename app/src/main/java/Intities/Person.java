package Intities;

import java.io.Serializable;
import java.util.ArrayList;

import Intities.Travels;

public class Person implements Serializable {
    String fullName ;
    String refLink ;
    String pictureURL ;
    String briefDescription ;
    int dateOfBirth ;
    int dateOfDeath ;
    String Country ;
    String Gender ;
    ArrayList<Travels> personTravels ;
    String category ;
    double Latitude ;
    double Longitude ;

    public Person() {
    }

    public Person(String fullName, String refLink, String pictureURL, String briefDescription, int dateOfBirth,
                  int dateOfDeath, String country, String gender, String category,
                  double Latitude, double Longitude) {
        this.fullName = fullName;
        this.refLink = refLink;
        this.pictureURL = pictureURL;
        this.briefDescription = briefDescription;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.Country = country;
        this.Gender = gender;
        this.category = category;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(int dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public ArrayList<Travels> getPersonTravels() {
        return personTravels;
    }

    public void setPersonTravels(ArrayList<Travels> personTravels) {
        this.personTravels = personTravels;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", refLink='" + refLink + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", Country='" + Country + '\'' +
                ", Gender='" + Gender + '\'' +
                ", personTravels=" + personTravels.toString() +
                ", category='" + category + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}
