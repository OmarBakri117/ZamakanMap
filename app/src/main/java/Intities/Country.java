package Intities;

import com.google.android.gms.maps.model.LatLng;

public class Country {
    private String countryName ;
    private double lat , lon ;
    private LatLng latLng ;

    public Country() {
    }

    public Country(String countryName, double lat, double lon) {
        this.countryName = countryName;
        this.lat = lat;
        this.lon = lon;
        this.latLng = new LatLng(lat,lon);
    }

    public String getCountryName() {
        return countryName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", latLng=" + latLng +
                '}';
    }
}
